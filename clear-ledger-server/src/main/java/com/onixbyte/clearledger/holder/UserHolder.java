package com.onixbyte.clearledger.holder;

import com.onixbyte.clearledger.data.domain.UserDomain;

public class UserHolder {

    private static final ThreadLocal<UserDomain> userHolder = new InheritableThreadLocal<>();

    public static UserDomain getCurrentUser() {
        return userHolder.get();
    }

    public static void setCurrentUser(UserDomain currentUser) {
        userHolder.set(currentUser);
    }

    public static void clearCurrentUser() {
        userHolder.remove();
    }

}
