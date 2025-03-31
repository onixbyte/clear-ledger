package com.onixbyte.clearledger.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility class for composing cache keys.
 * <p>
 * Provides methods to generate consistent cache key strings for various purposes, incorporating
 * the application name as a prefix for uniqueness.
 *
 * @author zihluwang
 */
@Component
public final class CacheKeyComposer {

    private final String appName;

    /**
     * Constructs a cache key composer with the application name.
     *
     * @param appName the name of the application, injected from the Spring property
     *                {@code spring.application.name}
     */
    @Autowired
    public CacheKeyComposer(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    /**
     * Generates a cache key for storing a verification code.
     *
     * @param audience the recipient identifier (e.g., email address) associated with the
     *                 verification code
     * @return the composed cache key in the format "{appName}:verification-code:code:{audience}"
     */
    public String getVerificationCodeKey(String audience) {
        return "%s:verification-code:code:%s".formatted(appName, audience);
    }


    /**
     * Generates a cache key for locking verification code requests.
     *
     * @param audience the recipient identifier (e.g., email address) associated with the lock
     * @return the composed cache key in the format "{appName}:verification-code:lock:{audience}"
     */
    public String getVerificationLockKey(String audience) {
        return "%s:verification-code:lock:%s".formatted(appName, audience);
    }

    /**
     * Generates a cache key for storing user data.
     *
     * @param username the username of the user
     * @return the composed cache key in the format "{appName}:user:{username}"
     */
    public String getUserKey(String username) {
        return "%s:user:%s".formatted(appName, username);
    }

    /**
     * Generates a cache key for storing serial numbers.
     *
     * @param tag the identifier for the serial number sequence
     * @return the composed cache key in the format "{appName}:serial:{tag}"
     */
    public String getSerialKey(String tag) {
        return "%s:serial:%s".formatted(appName, tag);
    }

}
