package me.kimgunwoo.auctionseats.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LettuceUtils {

    private final RedisTemplate<String, String> lettuceTemplate;

    public void save(String key, String value, long time) {
        lettuceTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    public String get(String key) {
        return lettuceTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(lettuceTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(lettuceTemplate.hasKey(key));
    }
}
