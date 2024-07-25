package me.kimgunwoo.auctionseats.domain.bid.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.bid.dto.response.BidInfoResponse;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.bid.redis.RedisSubscriber;
import me.kimgunwoo.auctionseats.domain.bid.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.bid.repository.SseRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.PointService;
import me.kimgunwoo.auctionseats.domain.user.service.UserService;
import me.kimgunwoo.auctionseats.global.annotaion.DistributedLock;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

import static me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant.AUCTION_BID_KEY_PREFIX;
import static me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant.BID_PRICE_INCREASE_PERCENT;
import static me.kimgunwoo.auctionseats.domain.bid.constant.BidConstant.AUCTION_SSE_PREFIX;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class BidServiceImpl implements BidService {
    private static final Long DEFAULT_SSE_TIMEOUT = 30 * 60 * 1000L;
    private static final String CONNECTED = "CONNECTED";
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final BidRedisService bidRedisService;
    private final PointService pointService;
    private final UserService userService;
    //Sse
    private final SseRepository sseRepository;
    private final RedisSubscriber redisSubscriber;

    @Override
    @DistributedLock(key = "#auctionId")
    public void bid(Long auctionId, BidRequest bidRequest, User loginUser) {
        Auction auction = getAuction(auctionId);
        boolean isEnded = auction.getIsEnded();

        if (isEnded) {
            throw new ApiException(ENDED_AUCTION);
        }

        User bidder = userService.findByUserId(loginUser.getId());
        long newBidPrice = bidRequest.getPrice();
        long currentBidPrice = bidRedisService.getBidPrice(auctionId)
                .orElseGet(() -> {
                    //redis에 입찰정보 없는 경우 재처리
                    bidRedisService.saveWithExpire(auction);
                    return auction.getStartPrice();
                });

        //DB에 입찰기록이 존재하는 경우
        Optional<Long> maxBidPriceOptional = getMaxBidPrice(auction);
        if (maxBidPriceOptional.isPresent()) {
            currentBidPrice = maxBidPriceOptional.get();
            bidRedisService.setBidPrice(auctionId, currentBidPrice);
        }

        validateBid(currentBidPrice, newBidPrice);
        updateBidderPoints(bidder, newBidPrice, currentBidPrice, auction);

        saveBid(bidder, newBidPrice, auction);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<BidInfoResponse> getMyBids(Long auctionId, User loginUser, Pageable pageable) {
        return bidRepository.getMyBids(auctionId, loginUser, pageable);
    }

    @Override
    public Optional<Long> getMaxBidPrice(Auction auction) {
        return bidRepository.getMaxBidPrice(auction);
    }

    @Override
    public SseEmitter subscribe(Long auctionId) {
        String channelName = AUCTION_SSE_PREFIX + auctionId;
        redisSubscriber.createChannel(channelName);
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_SSE_TIMEOUT);
        sseEmitter.onCompletion(() -> sseRepository.deleteAll(channelName));
        sseEmitter.onTimeout(() -> {
            sseRepository.deleteAll(channelName);
            sseEmitter.complete();
        });
        sseRepository.save(channelName, sseEmitter);
        try {
            //503 대비 더미데이터 send
            sseEmitter.send(SseEmitter.event()
                    .name(CONNECTED)
                    .data("subscribe"));
        } catch (IOException exception) {
            log.info("SSE Exception: {}", exception.getMessage());
            sseRepository.delete(channelName, sseEmitter);
        }
        return sseEmitter;
    }
    public void updateBidderPoints(User bidder, long newBidPrice, long currentBidPrice, Auction auction) {
        Optional<Bid> currentBid = getCurrentBid(auction);
        if (currentBid.isPresent()) {
            User currentBidder = currentBid.get().getUser();
            pointService.refundPoint(currentBidder, currentBidPrice);
        }
        //새 입찰자 포인트 차감 및 경매 입찰가 갱신
        pointService.usePoint(bidder, newBidPrice);
    }
    public void saveBid(User bidder, long newBidPrice, Auction auction) {
        Bid newBid = Bid.builder()
                .price(newBidPrice)
                .user(bidder)
                .auction(auction)
                .build();

        bidRepository.save(newBid);
        bidRedisService.setBidPrice(auction.getId(), newBidPrice);
    }
    public Auction getAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_AUCTION));
    }
    public Optional<Bid> getCurrentBid(Auction auction) {
        return bidRepository.findTopByAuctionOrderByIdDesc(auction);
    }
    /**
     * 입찰 기준
     * 1. 최근 입찰가보다 5% 이상 커야함
     * 2. 입찰가격만큼의 포인트를 가지고 있어야함
     */
    private static void validateBid(long currentBidPrice, long bidPrice) {
        //입찰 검증
        currentBidPrice += (long)(currentBidPrice * BID_PRICE_INCREASE_PERCENT);
        if (currentBidPrice > bidPrice) {
            throw new ApiException(BAD_REQUEST_BID);
        }
    }
}