package com.onixbyte.clearledger.config;

import com.onixbyte.clearledger.data.biz.BizUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class CacheConfig {

    @Bean
    public RedisTemplate<String, BizUser> userCache(RedisConnectionFactory redisConnectionFactory) {
        var userCache = new RedisTemplate<String, BizUser>();
        userCache.setConnectionFactory(redisConnectionFactory);
        userCache.setKeySerializer(RedisSerializer.string());

        userCache.setValueSerializer(new Jackson2JsonRedisSerializer<>(BizUser.class));

        userCache.afterPropertiesSet();
        return userCache;
    }

    @Bean
    public RedisTemplate<String, Long> serialCache(RedisConnectionFactory redisConnectionFactory) {
        var serialCache = new RedisTemplate<String, Long>();
        serialCache.setConnectionFactory(redisConnectionFactory);
        serialCache.setKeySerializer(RedisSerializer.string());

        serialCache.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));

        serialCache.afterPropertiesSet();
        return serialCache;
    }

}
