package com.onixbyte.clearledger.controller.advice;

import com.onixbyte.clearledger.data.response.BizExceptionResponse;
import com.onixbyte.clearledger.exception.BizException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * <p>
 * This class handles exceptions thrown by controllers and translates them into standardised
 * response formats. It ensures consistent error handling and provides meaningful feedback
 * to clients.
 * <p>
 * Specifically, this advice class handles custom exceptions such as {@link BizException} and
 * converts them into a structured {@link ResponseEntity} containing a {@link BizExceptionResponse}.
 *
 * @author zihluwang
 */
@RestControllerAdvice
public class ApplicationExceptionAdvice {

    /**
     * Handles {@link BizException} and returns a structured error response.
     * <p>
     * This method catches exceptions of type {@link BizException} and generates a
     * {@link ResponseEntity} containing a {@link BizExceptionResponse}. This response includes
     * details such as a timestamp and a custom message describing the error.
     *
     * @param bizException the business exception that was thrown
     * @return a {@link ResponseEntity} containing the error details encapsulated in
     * a {@link BizExceptionResponse}
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<BizExceptionResponse> handleBizException(BizException bizException) {
        return bizException.composeResponse();
    }

}
