package com.onixbyte.clearledger.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

}
