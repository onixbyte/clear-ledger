package com.onixbyte.clearledger.security;

import com.onixbyte.clearledger.data.biz.BizUser;

public class UserHolder {

    private static final ThreadLocal<BizUser> userHolder = new InheritableThreadLocal<>();

    public static BizUser getCurrentUser() {
        return userHolder.get();
    }

    public static void setCurrentUser(BizUser currentUser) {
        userHolder.set(currentUser);
    }

    public static void clearCurrentUser() {
        userHolder.remove();
    }

}
