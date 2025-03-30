package com.onixbyte.clearledger.data.response;

/**
 * A data transfer object for representing user responses.
 * <p>
 * This record encapsulates the response data for a user, including their identifier, username, and
 * email address.
 *
 * @param id       the unique identifier of the user
 * @param username the username of the user
 * @param email    the email address of the user
 * @author zihluwang
 */
public record UserResponse(
        String id,
        String username,
        String email
) {

    /**
     * Creates a new builder instance for constructing a {@link UserResponse} object.
     * <p>
     * This static method provides an entry point to the builder pattern, returning a new
     * {@link UserViewBuilder} instance for fluently setting the properties of a user response.
     *
     * @return a new {@link UserViewBuilder} instance
     */
    public static UserViewBuilder builder() {
        return new UserViewBuilder();
    }

    /**
     * Builder class for constructing {@link UserResponse} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link UserResponse} object,
     * ensuring flexible and readable construction.
     */
    public static class UserViewBuilder {
        private String id;
        private String username;
        private String email;

        private UserViewBuilder() {}

        /**
         * Sets the identifier of the user.
         *
         * @param id the unique identifier to set
         * @return this builder instance
         */
        public UserViewBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the username of the user.
         *
         * @param username the username to set
         * @return this builder instance
         */
        public UserViewBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email address of the user.
         *
         * @param email the email address to set
         * @return this builder instance
         */
        public UserViewBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Builds a {@link UserResponse} instance with the specified properties.
         *
         * @return a new {@link UserResponse} instance
         */
        public UserResponse build() {
            return new UserResponse(id, username, email);
        }
    }
}