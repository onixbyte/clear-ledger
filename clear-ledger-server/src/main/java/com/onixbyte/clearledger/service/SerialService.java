package com.onixbyte.clearledger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SerialService {

    private static final Logger log = LoggerFactory.getLogger(SerialService.class);
    private final String appName;
    private final RedisTemplate<String, Long> serialCache;

    public SerialService(@Value("${spring.application.name}") String appName, RedisTemplate<String, Long> serialCache) {
        this.appName = appName;
        this.serialCache = serialCache;
    }

    public String composeKey(String tag) {
        return "%s:serial:%s".formatted(appName, tag);
    }

    public Long nextSerial(String tag) {
        return serialCache.opsForValue().increment(composeKey(tag));
    }

    public void resetSerial(String tag) {
        serialCache.opsForValue().set(composeKey(tag), 0L);
    }

}
