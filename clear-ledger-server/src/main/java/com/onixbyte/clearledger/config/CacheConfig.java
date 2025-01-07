package com.onixbyte.clearledger.config;

import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class CacheConfig {

    @Bean
    public RedisTemplate<String, User> userCache(RedisConnectionFactory redisConnectionFactory) {
        var userCache = new RedisTemplate<String, User>();
        userCache.setConnectionFactory(redisConnectionFactory);
        userCache.setKeySerializer(RedisSerializer.string());

        var serializer = new Jackson2JsonRedisSerializer<>(User.class);
        userCache.setValueSerializer(serializer);

        userCache.afterPropertiesSet();
        return userCache;
    }

}
