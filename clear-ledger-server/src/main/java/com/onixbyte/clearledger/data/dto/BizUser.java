package com.onixbyte.clearledger.data.dto;

import com.onixbyte.clearledger.data.response.UserResponse;

/**
 * A data transfer object representing a user with business-specific details.
 * <p>
 * This record encapsulates internal user data, including the identifier, username, and email
 * address, used for passing user data internally.
 *
 * @param id       the unique identifier of the user
 * @param username the username of the user
 * @param email    the email address of the user
 * @author zihluwang
 */
public record BizUser(
        String id,
        String username,
        String email
) {

    /**
     * Creates a new builder for constructing a {@link BizUser} instance.
     *
     * @return a {@link BizUserBuilder} instance
     */
    public static BizUserBuilder builder() {
        return new BizUserBuilder();
    }

    /**
     * Builder class for constructing {@link BizUser} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link BizUser} object,
     * facilitating flexible and readable construction.
     */
    public static class BizUserBuilder {
        private String id;
        private String username;
        private String email;

        /**
         * Constructs an empty builder.
         */
        private BizUserBuilder() {
        }

        /**
         * Sets the identifier of the user.
         *
         * @param id the unique identifier of the user
         * @return this builder instance
         */
        public BizUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the username of the user.
         *
         * @param username the username of the user
         * @return this builder instance
         */
        public BizUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email address of the user.
         *
         * @param email the email address of the user
         * @return this builder instance
         */
        public BizUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Builds a {@link BizUser} instance with the specified properties.
         *
         * @return a new {@link BizUser} instance
         */
        public BizUser build() {
            return new BizUser(id, username, email);
        }
    }

    /**
     * Converts this {@link BizUser} instance into a {@link UserResponse}.
     * <p>
     * This method transforms the internal user data into a response object suitable for external
     * communication, such as API responses.
     *
     * @return a {@link UserResponse} containing the user's details
     */
    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(String.valueOf(id))
                .email(email)
                .username(username)
                .build();
    }
}