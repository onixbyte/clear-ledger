package com.onixbyte.clearledger.data.response;

import com.onixbyte.clearledger.data.view.BizLedgerView;

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
public record BizExceptionResponse(
        LocalDateTime timestamp,
        String message
) {

    public static BizExceptionResponseBuilder builder() {
        return new BizExceptionResponseBuilder();
    }

    public static class BizExceptionResponseBuilder {
        private LocalDateTime timestamp;
        private String message;

        private BizExceptionResponseBuilder() {
        }

        public BizExceptionResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public BizExceptionResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public BizExceptionResponse build() {
            return new BizExceptionResponse(timestamp, message);
        }
    }

}
