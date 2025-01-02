package com.onixbyte.clearledger.data.request;

public record UserRegisterRequest(
        String username,
        String password,
        String email
) {
}
