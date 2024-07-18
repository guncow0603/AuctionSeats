package me.kimgunwoo.auctionseats.domain.bid.redis;


import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisPublisher {
    private final LettuceUtils lettuceUtils;

    public void publish(String channelName, long bidPrice) {
        lettuceUtils.sendMessage(channelName, String.valueOf(bidPrice));
    }
}
