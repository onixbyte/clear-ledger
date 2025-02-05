package com.onixbyte.clearledger.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends BizException {

    /**
     * The constructor of this exception.
     *
     * @param message a customised error message
     */
    public ServiceUnavailableException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }

}
