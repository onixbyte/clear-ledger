package com.onixbyte.clearledger.security.token;

import com.onixbyte.clearledger.data.biz.BizUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UsernamePasswordToken implements Authentication, CredentialsContainer {

    private String principal;

    private String credentials;

    private boolean authenticated;

    private BizUser details;

    private List<GrantedAuthority> authorities;

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public void setDetails(BizUser details) {
        this.details = details;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public BizUser getDetails() {
        return details;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return principal;
    }

    public UsernamePasswordToken(String principal, String credentials) {
        this.authenticated = false;
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = null;
    }

    public UsernamePasswordToken(String principal, String credentials, List<GrantedAuthority> authorities) {
        this.authenticated = true;
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    public static UsernamePasswordToken unauthenticated(String principal, String credentials) {
        return new UsernamePasswordToken(principal, credentials);
    }

    public static UsernamePasswordToken authenticated(String principal, String credentials, List<GrantedAuthority> authorities) {
        return new UsernamePasswordToken(principal, credentials, authorities);
    }

    public static UsernamePasswordToken authenticated(BizUser user) {
        var authentication = new UsernamePasswordToken(user.username(), null, Collections.emptyList());
        authentication.setDetails(user);
        return authentication;
    }

    @Override
    public void eraseCredentials() {
        this.credentials = "";
    }
}
