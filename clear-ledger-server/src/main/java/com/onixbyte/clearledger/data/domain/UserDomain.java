package com.onixbyte.clearledger.data.domain;

import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

public record UserDomain(
        Long id,
        String username,
        String password,
        String email
) implements UserDetails {

    public static UserDomainBuilder builder() {
        return new UserDomainBuilder();
    }

    public static class UserDomainBuilder {
        private Long id;
        private String username;
        private String password;
        private String email;

        private UserDomainBuilder() {
        }

        public UserDomainBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDomainBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserDomainBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDomainBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDomain build() {
            return new UserDomain(id, username, password, email);
        }
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public UserResponse toView() {
        return UserResponse.builder()
                .id(String.valueOf(id))
                .username(username)
                .email(email)
                .build();
    }

    public User toPersistent() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .createdAt(null)
                .build();
    }

}
