package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.util.CacheKeyComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SerialService {

    private static final Logger log = LoggerFactory.getLogger(SerialService.class);

    private final RedisTemplate<String, Long> serialCache;
    private final CacheKeyComposer cacheKeyComposer;

    public SerialService(RedisTemplate<String, Long> serialCache, CacheKeyComposer cacheKeyComposer) {
        this.serialCache = serialCache;
        this.cacheKeyComposer = cacheKeyComposer;
    }

    public Long nextSerial(String tag) {
        var serial = Optional.ofNullable(serialCache.opsForValue().increment(cacheKeyComposer.getSerialKey(tag)))
                .orElse(1L);
        if (serial < 10_000L) {
            return serial;
        } else {
            throw BizException.serviceUnavailable("当前序号已超过当日最大上限，请明天再试");
        }
    }

    public void resetSerial(String tag) {
        serialCache.opsForValue().set(cacheKeyComposer.getSerialKey(tag), 0L);
    }

}
