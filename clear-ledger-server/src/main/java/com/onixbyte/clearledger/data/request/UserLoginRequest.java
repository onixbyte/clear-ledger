package com.onixbyte.clearledger.data.request;

public record UserLoginRequest(
        String username,
        String password
) {
}
