package com.onixbyte.clearledger.data.domain;

import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.view.UserView;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

@Builder
public record UserDomain(
        Long id,
        String username,
        String password,
        String email
) implements UserDetails {

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

    public UserView toView() {
        return UserView.builder()
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
