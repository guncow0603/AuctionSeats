package me.kimgunwoo.auctionseats.domain.bid.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimgunwoo.auctionseats.domain.auction.entity.Auction;
import me.kimgunwoo.auctionseats.global.event.AuctionEndEvent;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

import static me.kimgunwoo.auctionseats.domain.bid.constant.BidConstant.AUCTION_BID_KEY_PREFIX;





@Slf4j
@RequiredArgsConstructor
@Repository
public class BidRedisRepository {
    private final Codec codec = new TypedJsonJacksonCodec(Long.class);
    private final RedissonClient redissonClient;
    private final ApplicationEventPublisher publisher;

    public void saveWithExpire(Auction auction, long seconds) {
        RBucket<Long> bucket = getBucket(auction.getId());
        bucket.set(auction.getStartPrice(), Duration.ofSeconds(seconds));

        bucket.addListener((ExpiredObjectListener)name -> {
            publisher.publishEvent(
                    AuctionEndEvent.builder()
                            .id(auction.getId())
                            .build()
            );
            log.info("Auction Expired! id: {}", auction.getId());
        });
    }

    public Optional<Long> getValue(Long auctionId) {
        RBucket<Long> bucket = getBucket(auctionId);
        return Optional.of(bucket.get());
    }

    public void setValue(Long auctionId, Long bidPrice) {
        RBucket<Long> bucket = getBucket(auctionId);
        long remainTtl = bucket.remainTimeToLive();
        bucket.setIfExists(bidPrice, Duration.ofMillis(remainTtl));
    }

    public RBucket<Long> getBucket(Long auctionId) {
        return redissonClient.getBucket(AUCTION_BID_KEY_PREFIX + auctionId, codec);
    }

    public boolean isExpired(Long auctionId) {
        return getBucket(auctionId).remainTimeToLive() < 1;
    }
}