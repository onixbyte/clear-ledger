package com.onixbyte.clearledger.data.view;

import lombok.Builder;

@Builder
public record UserView(
        String id,
        String username,
        String email
) {
}
