package me.kimgunwoo.auctionseats.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LettuceUtils {

    public static final int REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 14;
    private final RedisTemplate<String, String> lettuceTemplate;


    public void save(String key, String value, Integer min) {
        lettuceTemplate.opsForValue().set(key, value, min, TimeUnit.SECONDS);
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
