package com.onixbyte.clearledger.exception;

import com.onixbyte.clearledger.data.response.BizExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * This is a business exception that can specify the http status and a customised message. All
 * exceptions that extends from this exception will be captured by
 * {@link com.onixbyte.clearledger.controller.advice.ApplicationExceptionAdvice#handleBizException(BizException)}
 * and return a {@link BizExceptionResponse} automatically to the web.
 *
 * @author zihluwang
 */
public class BizException extends RuntimeException {

    /**
     * The http status.
     */
    private final HttpStatus status;

    /**
     * The timestamp indicates when the exception is thrown.
     */
    private final LocalDateTime timestamp;

    /**
     * The constructor of this exception.
     *
     * @param status  the Http Status
     * @param message a customised error message
     */
    public BizException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Compose a canonical response entity with the given information by this exception.
     *
     * @return a canonical response entity
     */
    public ResponseEntity<BizExceptionResponse> composeResponse() {
        return ResponseEntity.status(status)
                .body(BizExceptionResponse.builder()
                        .timestamp(timestamp)
                        .message(getMessage())
                        .build());
    }

    /**
     * Construct a BizException with status Bad Request and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Bad Request
     */
    public static BizException badRequest(String message) {
        return new BizException(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Construct a BizException with status Unauthorized and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Unauthorized
     */
    public static BizException unauthorised(String message) {
        return new BizException(HttpStatus.UNAUTHORIZED, message);
    }

    /**
     * Construct a BizException with status Forbidden and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Forbidden
     */
    public static BizException forbidden(String message) {
        return new BizException(HttpStatus.FORBIDDEN, message);
    }

    /**
     * Construct a BizException with status Not Found and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Not Found
     */
    public static BizException notFound(String message) {
        return new BizException(HttpStatus.NOT_FOUND, message);
    }

    /**
     * Construct a BizException with status Not Acceptable and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Not Acceptable
     */
    public static BizException notAcceptable(String message) {
        return new BizException(HttpStatus.NOT_ACCEPTABLE, message);
    }

    /**
     * Construct a BizException with status Request Timeout and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Request Timeout
     */
    public static BizException requestTimeout(String message) {
        return new BizException(HttpStatus.REQUEST_TIMEOUT, message);
    }

    /**
     * Construct a BizException with status Conflict and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Conflict
     */
    public static BizException conflict(String message) {
        return new BizException(HttpStatus.CONFLICT, message);
    }

    /**
     * Construct a BizException with status Too Early and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Too Early
     */
    public static BizException tooEarly(String message) {
        return new BizException(HttpStatus.TOO_EARLY, message);
    }

    /**
     * Construct a BizException with status Precondition Failed and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Precondition Failed
     */
    public static BizException preconditionFailed(String message) {
        return new BizException(HttpStatus.PRECONDITION_FAILED, message);
    }

    /**
     * Construct a BizException with status Unsupported Media Type and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Unsupported Media Type
     */
    public static BizException unsupportedMediaType(String message) {
        return new BizException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
    }

    /**
     * Construct a BizException with status Too Many Requests and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Too Many Requests
     */
    public static BizException tooManyRequests(String message) {
        return new BizException(HttpStatus.TOO_MANY_REQUESTS, message);
    }

    /**
     * Construct a BizException with status Unavailable For Legal Reasons and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Unavailable For Legal Reasons
     */
    public static BizException unavailableForLegalReasons(String message) {
        return new BizException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, message);
    }

    /**
     * Construct a BizException with status Internal Server Error and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Internal Server Error
     */
    public static BizException internalServerError(String message) {
        return new BizException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * Construct a BizException with status Not Implemented and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Not Implemented
     */
    public static BizException notImplemented(String message) {
        return new BizException(HttpStatus.NOT_IMPLEMENTED, message);
    }

    /**
     * Construct a BizException with status Bad Gateway and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Bad Gateway
     */
    public static BizException badGateway(String message) {
        return new BizException(HttpStatus.BAD_GATEWAY, message);
    }

    /**
     * Construct a BizException with status Service Unavailable and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Service Unavailable
     */
    public static BizException serviceUnavailable(String message) {
        return new BizException(HttpStatus.SERVICE_UNAVAILABLE, message);
    }

    /**
     * Construct a BizException with status Gateway Timeout and given message.
     *
     * @param message a customised exception message
     * @return a BizException whose status is Gateway Timeout
     */
    public static BizException gatewayTimeout(String message) {
        return new BizException(HttpStatus.GATEWAY_TIMEOUT, message);
    }

    /**
     * Retrieves the HTTP status associated with this exception.
     *
     * @return the HTTP status of the exception
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Retrieves the timestamp when this exception was thrown.
     *
     * @return the timestamp of the exception
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
