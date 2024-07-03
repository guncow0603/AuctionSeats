package me.kimgunwoo.auctionseats.domain.auction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.entity.Bid;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRedissonRepository;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.auction.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.domain.user.repository.UserRepository;
import me.kimgunwoo.auctionseats.global.annotaion.DistributedLock;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.springframework.stereotype.Service;

import static me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant.AUCTION_BID_KEY_PREFIX;
import static me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant.BID_PRICE_INCREASE_PERCENT;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class BidServiceImpl implements BidService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final BidRepository bidRepository;
    private final AuctionRedissonRepository redissonRepository;

    @Override
    @DistributedLock(key = "T(me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant).AUCTION_BID_KEY_PREFIX.concat(#auctionId)")
    public void bid(Long auctionId, BidRequest bidRequest, User loginUser) {
        String key = AUCTION_BID_KEY_PREFIX + auctionId;

        if (redissonRepository.isExpired(key)) {
            throw new ApiException(ENDED_AUCTION);
        }

        long point = userRepository.findPointById(loginUser.getId());

        long bidPrice = bidRequest.getPrice();
        long currentBidPrice = redissonRepository.getValue(key);
        validateBid(point, currentBidPrice, bidPrice);;

        //경매 엔티티 입찰가 갱신 및 입찰 테이블 save
        Auction auction = auctionRepository.getReferenceById(auctionId);

        auction.updateBidPrice(bidPrice);

        Bid bid = Bid.builder()
                .price(bidPrice)
                .user(loginUser)
                .auction(auction)
                .build();

        bidRepository.save(bid);

        redissonRepository.setValue(key, bidPrice);
    }

    private static void validateBid(long point, long currentBidPrice, long bidPrice) {
        currentBidPrice += (long)(currentBidPrice * BID_PRICE_INCREASE_PERCENT);

        if (currentBidPrice > bidPrice) {
            throw new ApiException(BAD_REQUEST_BID);
        }

        //포인트가 부족한 경우
        if (point < bidPrice) {
            throw new ApiException(NOT_ENOUGH_POINT);
        }
    }
}