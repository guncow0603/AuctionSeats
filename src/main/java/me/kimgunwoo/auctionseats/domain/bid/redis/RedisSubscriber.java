package me.kimgunwoo.auctionseats.domain.bid.redis;

import com.amazonaws.services.s3.internal.eventstreaming.Message;
import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.global.util.LettuceUtils;
import org.redisson.api.listener.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {
    private final Map<String, String> channelLists = new ConcurrentHashMap<>();

    private final RedisMessageListenerContainer redisContainer;
    private final LettuceUtils lettuceUtils;
    private final SseService sseService;

    public void createChannel(String channelName) {
        if (!channelLists.containsKey(channelName)) {
            redisContainer.addMessageListener(this, new ChannelTopic(channelName));
            channelLists.putIfAbsent(channelName, channelName);
        }
    }

    public void removeChannel(String channelName) {
        if (channelLists.containsKey(channelName)) {
            redisContainer.removeMessageListener(this, new ChannelTopic(channelName));
            // value = null 이면 해당 키 값 제거
            channelLists.computeIfPresent(channelName,(key, value) -> channelLists.remove(key));
        }
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channelName = lettuceUtils.deserializeString(message.getChannel());
        String productPrice = lettuceUtils.deserializeString(message.getBody());
        int connectedSseEmittersSize  = sseService.notify(channelName, productPrice);
        if (connectedSseEmittersSize == 0) {
            removeChannel(channelName);
        }
    }
}