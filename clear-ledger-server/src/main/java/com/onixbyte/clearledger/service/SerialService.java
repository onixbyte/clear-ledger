package com.onixbyte.clearledger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0 0 1 * ?")
    public void reset() {
        log.info("Resetting serial value.");

        var keys = serialCache.keys(composeKey("*"));
        keys.forEach((key) -> {
            serialCache.opsForValue().set(key, 0L);
        });

        log.info("All serial has been reset.");
    }

}
