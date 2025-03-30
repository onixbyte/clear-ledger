package com.onixbyte.clearledger.data.response;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A data transfer object for representing business exception responses.
 * <p>
 * This record encapsulates the response data for a business exception, including the timestamp of
 * the occurrence and the error message.
 *
 * @param timestamp the timestamp when the exception occurred
 * @param message   the error message describing the exception
 * @author zihluwang
 */
public record BizExceptionResponse(
        LocalDateTime timestamp,
        String message
) {

    /**
     * Creates a new builder instance for constructing a {@link BizExceptionResponse} object.
     * <p>
     * This static method provides an entry point to the builder pattern, returning a new
     * {@link BizExceptionResponseBuilder} instance for fluently setting the properties of a
     * business exception response.
     *
     * @return a new {@link BizExceptionResponseBuilder} instance
     */
    public static BizExceptionResponseBuilder builder() {
        return new BizExceptionResponseBuilder();
    }

    /**
     * Builder class for constructing {@link BizExceptionResponse} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link BizExceptionResponse}
     * object, ensuring flexible and readable construction. If the timestamp is not provided, it
     * defaults to the current time.
     */
    public static class BizExceptionResponseBuilder {
        private LocalDateTime timestamp;
        private String message;

        private BizExceptionResponseBuilder() {
        }

        /**
         * Sets the timestamp of the exception.
         *
         * @param timestamp the timestamp to set
         * @return this builder instance
         */
        public BizExceptionResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Sets the error message of the exception.
         *
         * @param message the error message to set
         * @return this builder instance
         */
        public BizExceptionResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Builds a {@link BizExceptionResponse} instance with the specified properties.
         * <p>
         * If the timestamp is not set, it defaults to the current date and time using
         * {@link LocalDateTime#now()}.
         *
         * @return a new {@link BizExceptionResponse} instance
         */
        public BizExceptionResponse build() {
            var _timestamp = Optional.ofNullable(timestamp)
                    .orElseGet(LocalDateTime::now);
            return new BizExceptionResponse(_timestamp, message);
        }
    }
}