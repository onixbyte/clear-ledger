package com.onixbyte.clearledger.exception;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends BizException {

    /**
     * The constructor of this exception.
     *
     * @param message a customised error message
     */
    public UnauthenticatedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthenticatedException() {
        super(HttpStatus.UNAUTHORIZED, "You haven't login yet.");
    }

}
