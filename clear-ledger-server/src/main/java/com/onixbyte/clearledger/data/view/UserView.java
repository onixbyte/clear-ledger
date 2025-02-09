package com.onixbyte.clearledger.data.view;

public record UserView(
        String id,
        String username,
        String email
) {

    public static UserViewBuilder builder() {
        return new UserViewBuilder();
    }

    public static class UserViewBuilder {
        private String id;
        private String username;
        private String email;

        private UserViewBuilder() {}

        public UserViewBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserViewBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserViewBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserView build() {
            return new UserView(id, username, email);
        }
    }
}

