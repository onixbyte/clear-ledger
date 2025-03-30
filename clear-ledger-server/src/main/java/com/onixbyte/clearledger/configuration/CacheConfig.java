package com.onixbyte.clearledger.configuration;

import com.onixbyte.clearledger.data.dto.BizUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Configuration class for Redis caching.
 * <p>
 * This class provides configuration for Redis caching in a Spring application by defining
 * customised {@link RedisTemplate} beans. Each template is tailored to specific data types used
 * within the application, such as user data, serial numbers, and verification codes. These
 * templates utilise a {@link RedisConnectionFactory} to connect to a Redis instance and apply
 * appropriate serialisation strategies for keys and values.
 *
 * @author zihluwang
 */
@Configuration
public class CacheConfig {

    /**
     * Configures a Redis template for caching user data.
     * <p>
     * This method creates a {@link RedisTemplate} bean for storing and retrieving {@link BizUser}
     * objects in Redis. It uses a string serializer for keys and a JSON serializer for values,
     * enabling efficient storage of user data in a structured format. The template is connected to
     * the provided {@link RedisConnectionFactory} and initialised for use within the application.
     *
     * @param redisConnectionFactory the factory for establishing a connection to the Redis server
     * @return a configured {@link RedisTemplate} for caching {@link BizUser} objects
     */
    @Bean
    public RedisTemplate<String, BizUser> userCache(RedisConnectionFactory redisConnectionFactory) {
        var userCache = new RedisTemplate<String, BizUser>();
        userCache.setConnectionFactory(redisConnectionFactory);
        userCache.setKeySerializer(RedisSerializer.string());
        userCache.setValueSerializer(new Jackson2JsonRedisSerializer<>(BizUser.class));

        userCache.afterPropertiesSet();
        return userCache;
    }

    /**
     * Configures a Redis template for caching serial numbers.
     * <p>
     * This method defines a {@link RedisTemplate} bean for storing and retrieving {@link Long}
     * values as serial numbers in Redis. It employs a string serializer for keys and a JSON
     * serializer for values, ensuring serial numbers are stored in a consistent and readable
     * format. The template is linked to the provided {@link RedisConnectionFactory} and prepared
     * for application use.
     *
     * @param redisConnectionFactory the factory for establishing a connection to the Redis server
     * @return a configured {@link RedisTemplate} for caching {@link Long} serial numbers
     */
    @Bean
    public RedisTemplate<String, Long> serialCache(RedisConnectionFactory redisConnectionFactory) {
        var serialCache = new RedisTemplate<String, Long>();
        serialCache.setConnectionFactory(redisConnectionFactory);
        serialCache.setKeySerializer(RedisSerializer.string());
        serialCache.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));

        serialCache.afterPropertiesSet();
        return serialCache;
    }

    /**
     * Configures a Redis template for caching verification codes.
     * <p>
     * This method establishes a {@link RedisTemplate} bean for storing and retrieving
     * {@link String} values as verification codes in Redis. It uses string serializers for both
     * keys and values, providing a straightforward approach to caching simple text-based codes.
     * The template is connected to the provided {@link RedisConnectionFactory} and initialised for
     * use within the application.
     *
     * @param redisConnectionFactory the factory for establishing a connection to the Redis server
     * @return a configured {@link RedisTemplate} for caching {@link String} verification codes
     */
    @Bean
    public RedisTemplate<String, String> verificationCodeCache(RedisConnectionFactory redisConnectionFactory) {
        var verificationCodeCache = new RedisTemplate<String, String>();
        verificationCodeCache.setConnectionFactory(redisConnectionFactory);
        verificationCodeCache.setKeySerializer(RedisSerializer.string());
        verificationCodeCache.setValueSerializer(RedisSerializer.string());

        verificationCodeCache.afterPropertiesSet();
        return verificationCodeCache;
    }
}