package me.kimgunwoo.auctionseats.global.config;


import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${spring.data.redis.cluster.nodes:}")
    private List<String> redisClusterNodes;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (activeProfile.equals("local")) {
            config.useSingleServer()
                    .setAddress("redis://" + "localhost" + ":" + 6379);
        } else {
            config.useClusterServers()
                    .setScanInterval(3000)
                    .addNodeAddress(
                            "redis://localhost:6379",
                            "redis://localhost:6380",
                            "redis://localhost:6381"
                    );
        }
        return Redisson.create(config);
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        if (activeProfile.equals("local")) {
            return new LettuceConnectionFactory("localhost", 6379 );
        } else {
            // 클러스터 환경: Redis 클러스터 설정
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration()
                    .clusterNode("localhost", 6379)
                    .clusterNode("localhost", 6380)
                    .clusterNode("localhost", 6381);
            LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                    .clientOptions(ClientOptions.builder()
                            .socketOptions(SocketOptions.builder()
                                    .connectTimeout(Duration.ofMillis(5000L)).build())
                            .build())
                    .commandTimeout(Duration.ofSeconds(5000L))
                    .build();
            return new LettuceConnectionFactory(clusterConfiguration, clientConfiguration);
        }
    }

    @Bean
    public RedisTemplate<String, Object> lettuceTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory());
        return container;
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory cf) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(30L)); // 캐쉬 저장 시간 30분 설정

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
