package com.onixbyte.clearledger.data.dto;

import com.onixbyte.clearledger.data.response.UserResponse;

/**
 * BizUser is used to pass the user data internally.
 *
 * @param id       user id
 * @param username username
 * @param email    user's email address
 */
public record BizUser(
        String id,
        String username,
        String email
) {

    public static BizUserBuilder builder() {
        return new BizUserBuilder();
    }

    public static class BizUserBuilder {
        private String id;
        private String username;
        private String email;

        private BizUserBuilder() {
        }

        public BizUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BizUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public BizUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public BizUser build() {
            return new BizUser(id, username, email);
        }
    }

    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(String.valueOf(id))
                .email(email)
                .username(username)
                .build();
    }

}
