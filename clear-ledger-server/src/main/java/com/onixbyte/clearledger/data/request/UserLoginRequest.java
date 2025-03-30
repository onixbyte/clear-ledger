package com.onixbyte.clearledger.data.request;

/**
 * A data transfer object for user login requests.
 * <p>
 * This record encapsulates the request data required for a user to log in, including their username
 * and password.
 *
 * @param username the username of the user attempting to log in
 * @param password the password of the user attempting to log in
 * @author zihluwang
 */
public record UserLoginRequest(
        String username,
        String password
) {
}
