package com.onixbyte.clearledger.data.request;

/**
 * A data transfer object for user registration requests.
 * <p>
 * This record encapsulates the request data required to register a new user, including their
 * username, password, email, and verification code.
 *
 * @param username         the username of the new user
 * @param password         the password of the new user
 * @param email            the email address of the new user
 * @param verificationCode the verification code sent to the user's email
 * @author zihluwang
 */
public record UserRegisterRequest(
        String username,
        String password,
        String email,
        String verificationCode
) {
}
