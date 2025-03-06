package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.biz.BizUser;
import com.onixbyte.clearledger.data.response.UserResponse;

import java.time.LocalDateTime;

@Table("users")
public class User {

    @Id(keyType = KeyType.None)
    private String id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    public User() {
    }

    public User(String id, String username, String password, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String id;
        private String username;
        private String password;
        private String email;
        private LocalDateTime createdAt;

        private UserBuilder() {
        }

        public UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username){
            this.username = username;
            return this;
        }

        public UserBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt){
            this.createdAt = createdAt;
            return this;
        }

        public User build() {
            return new User(id, username, password, email, createdAt);
        }
    }

    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(String.valueOf(getId()))
                .username(getUsername())
                .email(getEmail())
                .build();
    }

    public BizUser toBiz() {
        return BizUser.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();
    }

}
