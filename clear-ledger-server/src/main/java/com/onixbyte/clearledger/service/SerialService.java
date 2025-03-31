package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.util.CacheKeyComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for managing serial numbers using a Redis cache.
 * <p>
 * Provides methods to generate and reset incremental serial numbers for various tags, with a daily
 * limit enforced to prevent exhaustion.
 *
 * @author zihluwang
 */
@Service
public class SerialService {

    private static final Logger log = LoggerFactory.getLogger(SerialService.class);

    private final RedisTemplate<String, Long> serialCache;
    private final CacheKeyComposer cacheKeyComposer;

    /**
     * Constructs a serial service with required dependencies.
     *
     * @param serialCache      the Redis template for caching serial numbers
     * @param cacheKeyComposer the utility for composing cache keys
     */
    @Autowired
    public SerialService(RedisTemplate<String, Long> serialCache, CacheKeyComposer cacheKeyComposer) {
        this.serialCache = serialCache;
        this.cacheKeyComposer = cacheKeyComposer;
    }

    /**
     * Generates the next serial number for the specified tag.
     * <p>
     * Increments the serial number stored in Redis for the given tag, with a maximum limit
     * of 10,000 per day. If the limit is exceeded, an exception is thrown.
     *
     * @param tag the identifier for the serial number sequence
     * @return the next serial number
     * @throws BizException if the serial number exceeds the daily maximum limit of 10,000
     */
    public Long nextSerial(String tag) {
        var serial = Optional.ofNullable(serialCache.opsForValue().increment(cacheKeyComposer.getSerialKey(tag)))
                .orElse(1L);
        if (serial < 10_000L) {
            return serial;
        } else {
            throw BizException.serviceUnavailable("当前序号已超过当日最大上限，请明天再试");
        }
    }

    /**
     * Resets the serial number for the specified tag to zero.
     *
     * @param tag the identifier for the serial number sequence to reset
     */
    public void resetSerial(String tag) {
        serialCache.opsForValue().set(cacheKeyComposer.getSerialKey(tag), 0L);
    }

}
