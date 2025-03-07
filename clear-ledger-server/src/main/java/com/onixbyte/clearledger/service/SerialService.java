package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.exception.BizException;
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
        var serial = Optional.ofNullable(serialCache.opsForValue().increment(composeKey(tag)))
                .orElse(1L);
        if (serial < 10_000L) {
            return serial;
        } else {
            throw new BizException(HttpStatus.SERVICE_UNAVAILABLE, "当前序号已超过当日最大上限，请明天再试");
        }
    }

    public void resetSerial(String tag) {
        serialCache.opsForValue().set(composeKey(tag), 0L);
    }

}
