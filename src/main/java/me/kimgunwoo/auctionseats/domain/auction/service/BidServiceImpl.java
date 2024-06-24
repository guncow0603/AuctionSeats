package me.kimgunwoo.auctionseats.domain.auction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.dto.request.BidRequest;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.domain.auction.entity.Bid;
import me.kimgunwoo.auctionseats.domain.auction.repository.AuctionRepository;
import me.kimgunwoo.auctionseats.domain.auction.repository.BidRepository;
import me.kimgunwoo.auctionseats.domain.user.entity.User;
import me.kimgunwoo.auctionseats.global.annotaion.DistributedLock;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import static me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant.AUCTION_BID_KEY_PREFIX;
import static me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant.BID_PRICE_INCREASE_PERCENT;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.BAD_REQUEST_BID;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.ENDED_AUCTION;

@RequiredArgsConstructor
@Service
@Slf4j
public class BidServiceImpl implements BidService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final RedissonClient redissonClient;

    @Override
    @DistributedLock(key = "T(me.kimgunwoo.auctionseats.domain.auction.constant.AuctionConstant).AUCTION_BID_KEY_PREFIX.concat(#auctionId)")
    public void bid(Long auctionId, BidRequest bidRequest, User loginUser) {
        String key = AUCTION_BID_KEY_PREFIX + auctionId;
        RAtomicLong currentBid = redissonClient.getAtomicLong(key);

        if (currentBid.remainTimeToLive() < 1) {
            throw new ApiException(ENDED_AUCTION);
        }

        Auction auction = auctionRepository.getReferenceById(auctionId);
        long currentBidPrice = redissonClient.getAtomicLong(key).get();
        long increaseBidPrice = (long)(currentBidPrice * BID_PRICE_INCREASE_PERCENT);
        long bidPrice = bidRequest.getPrice();

        if (increaseBidPrice > bidPrice) {
            throw new ApiException(BAD_REQUEST_BID);
        }

        //경매 엔티티 입찰가 갱신 및 입찰 테이블 save
        auction.updateBidPrice(bidPrice);
        Bid bid = Bid.builder()
                .price(bidPrice)
                .user(loginUser)
                .auction(auction)
                .build();

        bidRepository.save(bid);
        currentBid.set(bidPrice);
    }
}