package com.onixbyte.clearledger.data.response;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * A response object representing details of a business exception.
 * <p>
 * This record provides a timestamp of when the exception occurred and the associated message.
 *
 * @param timestamp the timestamp indicating when the exception occurred
 * @param message   the message describing the exception
 * @author zihluwang
 */
@Builder
public record BizExceptionResponse(
        LocalDateTime timestamp,
        String message
) {
}
