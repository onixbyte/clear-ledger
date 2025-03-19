package com.onixbyte.clearledger.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class CacheKeyComposer {

    private final String appName;

    public CacheKeyComposer(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    public String getVerificationCodeKey(String audience) {
        return "%s:verification-code:code:%s".formatted(appName, audience);
    }

    public String getVerificationLockKey(String audience) {
        return "%s:verification-code:lock:%s".formatted(appName, audience);
    }

    public String getUserKey(String username) {
        return "%s:user:%s".formatted(appName, username);
    }

    public String getSerialKey(String tag) {
        return "%s:serial:%s".formatted(appName, tag);
    }

}
