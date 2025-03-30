package com.onixbyte.clearledger.security;

import com.onixbyte.clearledger.data.dto.BizUser;

/**
 * A utility class for managing the current authenticated user in a thread-local context.
 * <p>
 * Uses a {@link ThreadLocal} to store and retrieve the current {@link BizUser} across the application.
 *
 * @author zihluwang
 */
public class UserHolder {

    private static final ThreadLocal<BizUser> userHolder = new InheritableThreadLocal<>();

    /**
     * Retrieves the current authenticated user.
     *
     * @return the current {@link BizUser}, or null if not set
     */
    public static BizUser getCurrentUser() {
        return userHolder.get();
    }

    /**
     * Sets the current authenticated user.
     *
     * @param currentUser the {@link BizUser} to set as the current user
     */
    public static void setCurrentUser(BizUser currentUser) {
        userHolder.set(currentUser);
    }

    /**
     * Clears the current authenticated user from the thread-local storage.
     */
    public static void clearCurrentUser() {
        userHolder.remove();
    }
}