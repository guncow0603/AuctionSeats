package me.kimgunwoo.auctionseats.domain.bid.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.bid.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.bid.entity.Bid;
import me.kimgunwoo.auctionseats.domain.bid.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.service.PointService;
import me.kimgunwoo.auctionseats.global.annotaion.DistributedLock;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

import static me.kimgunwoo.auctionseats.domain.bid.constant.BidConstant.BID_PRICE_INCREASE_PERCENT;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class BidServiceImpl implements BidService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final BidRedisService bidRedisService;
    private final PointService pointService;

    @Override
    @DistributedLock(key = "T(com.sparta.ticketauction.domain.auction.constant.AuctionConstant).AUCTION_BID_KEY_PREFIX.concat(#auctionId)")
    public void bid(Long auctionId, BidRequest bidRequest, User bidder) {
        //redis에 경매 정보 확인
        if (bidRedisService.isExpired(auctionId)) {
            throw new ApiException(ENDED_AUCTION);
        }

        //입찰 검증
        long newBidPrice = bidRequest.getPrice();
        long currentBidPrice = bidRedisService.getBidPrice(auctionId);
        validateBid(currentBidPrice, newBidPrice);

        //기존 입찰자, 새입찰자 포인트 업데이트
        Auction auction = getAuction(auctionId);
        updateBidderPoints(bidder, newBidPrice, currentBidPrice, auction);
        //새 입찰 등록
        saveBid(bidder, newBidPrice, auction);
    }

    public void updateBidderPoints(User bidder, long newBidPrice, long currentBidPrice, Auction auction) {
        Optional<Bid> currentBid = getCurrentBid(auction);
        if (currentBid.isPresent()) {
            User currentBidder = currentBid.get().getUser();
            pointService.chargePoint(currentBidder, currentBidPrice);
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

        auction.updateBidPrice(newBidPrice);
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