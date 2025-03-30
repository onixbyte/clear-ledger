package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.dto.BizUser;
import com.onixbyte.clearledger.data.response.UserResponse;

import java.time.LocalDateTime;

/**
 * Entity class representing a user in the database.
 * <p>
 * This class maps to the "users" table and defines the structure of a user, including their
 * identifier, username, password, email, and creation timestamp. It provides a builder pattern for
 * construction and methods to convert to {@link UserResponse} and {@link BizUser} objects.
 *
 * @author zihluwang
 */
@Table("users")
public class User {

    /**
     * The unique identifier of the user.
     */
    @Id(keyType = KeyType.None)
    private String id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user (stored hashed).
     */
    private String password;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The timestamp when the user was created.
     */
    private LocalDateTime createdAt;

    /**
     * Default constructor required by MyBatis-Flex.
     */
    public User() {
    }

    /**
     * Constructs a user with the specified properties.
     *
     * @param id the unique identifier
     * @param username the username
     * @param password the password (hashed)
     * @param email the email address
     * @param createdAt the creation timestamp
     */
    public User(String id, String username, String password, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the user's identifier.
     *
     * @return the unique identifier of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's identifier.
     *
     * @param id the unique identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the user's username.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the user's password.
     *
     * @return the password of the user (typically hashed)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set (typically hashed)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the user's email address.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the creation timestamp of the user.
     *
     * @return the timestamp when the user was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the user.
     *
     * @param createdAt the timestamp to set for when the user was created
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Creates a new builder for constructing a {@link User} instance.
     *
     * @return a {@link UserBuilder} instance
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * Builder class for constructing {@link User} instances.
     * <p>
     * This class provides a fluent interface to set the properties of a {@link User} object,
     * facilitating flexible and readable construction.
     */
    public static class UserBuilder {
        private String id;
        private String username;
        private String password;
        private String email;
        private LocalDateTime createdAt;

        private UserBuilder() {
        }

        /**
         * Sets the identifier of the user.
         *
         * @param id the unique identifier
         * @return this builder instance
         */
        public UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the username of the user.
         *
         * @param username the username
         * @return this builder instance
         */
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the password of the user.
         *
         * @param password the password (hashed)
         * @return this builder instance
         */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the email address of the user.
         *
         * @param email the email address
         * @return this builder instance
         */
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the creation timestamp of the user.
         *
         * @param createdAt the creation timestamp
         * @return this builder instance
         */
        public UserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * Builds a {@link User} instance with the specified properties.
         *
         * @return a new {@link User} instance
         */
        public User build() {
            return new User(id, username, password, email, createdAt);
        }
    }

    /**
     * Converts this {@link User} to a {@link UserResponse}.
     * <p>
     * This method transforms the user entity into a response object suitable for external communication.
     *
     * @return a {@link UserResponse} instance
     */
    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(String.valueOf(getId()))
                .username(getUsername())
                .email(getEmail())
                .build();
    }

    /**
     * Converts this {@link User} to a {@link BizUser}.
     * <p>
     * This method transforms the user entity into a business-specific DTO for internal use.
     *
     * @return a {@link BizUser} instance
     */
    public BizUser toBiz() {
        return BizUser.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }
}