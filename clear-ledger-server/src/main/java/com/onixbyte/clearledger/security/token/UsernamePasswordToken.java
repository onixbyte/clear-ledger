package com.onixbyte.clearledger.security.token;

import com.onixbyte.clearledger.data.dto.BizUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An authentication token for username and password-based authentication.
 * <p>
 * This class implements {@link Authentication} and {@link CredentialsContainer} to represent a user's
 * authentication state, including their principal (username), credentials (password), authorities,
 * and additional details stored as a {@link BizUser} object.
 *
 * @author zihluwang
 */
public class UsernamePasswordToken implements Authentication, CredentialsContainer {

    private String principal;
    private String credentials;
    private boolean authenticated;
    private BizUser details;
    private List<GrantedAuthority> authorities;

    /**
     * Sets the principal (username) for this authentication token.
     *
     * @param principal the username to set
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * Sets the credentials (password) for this authentication token.
     *
     * @param credentials the password to set
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     * Sets the user details for this authentication token.
     *
     * @param details the {@link BizUser} object containing user details
     */
    public void setDetails(BizUser details) {
        this.details = details;
    }

    /**
     * Sets the authorities (permissions) for this authentication token.
     *
     * @param authorities the list of {@link GrantedAuthority} objects representing user permissions
     */
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Retrieves the authorities granted to the authenticated user.
     *
     * @return a collection of {@link GrantedAuthority} objects, or null if not set
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Retrieves the credentials (password) of the user.
     *
     * @return the password, or an empty string if erased
     */
    @Override
    public String getCredentials() {
        return credentials;
    }

    /**
     * Retrieves the detailed user information.
     *
     * @return the {@link BizUser} object containing user details, or null if not set
     */
    @Override
    public BizUser getDetails() {
        return details;
    }

    /**
     * Retrieves the principal (username) of the user.
     *
     * @return the username
     */
    @Override
    public String getPrincipal() {
        return principal;
    }

    /**
     * Indicates whether the user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets the authentication status of the token.
     *
     * @param authenticated true to mark as authenticated, false otherwise
     */
    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Retrieves the name of the principal (username).
     *
     * @return the username
     */
    @Override
    public String getName() {
        return principal;
    }

    /**
     * Constructs an unauthenticated token with the given principal and credentials.
     *
     * @param principal   the username
     * @param credentials the password
     */
    public UsernamePasswordToken(String principal, String credentials) {
        this.authenticated = false;
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = null;
    }

    /**
     * Constructs an authenticated token with the given principal, credentials, and authorities.
     *
     * @param principal   the username
     * @param credentials the password
     * @param authorities the list of {@link GrantedAuthority} objects
     */
    public UsernamePasswordToken(String principal, String credentials, List<GrantedAuthority> authorities) {
        this.authenticated = true;
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    /**
     * Creates an unauthenticated token with the specified principal and credentials.
     *
     * @param principal   the username
     * @param credentials the password
     * @return a new {@link UsernamePasswordToken} instance marked as unauthenticated
     */
    public static UsernamePasswordToken unauthenticated(String principal, String credentials) {
        return new UsernamePasswordToken(principal, credentials);
    }

    /**
     * Creates an authenticated token with the specified principal, credentials, and authorities.
     *
     * @param principal   the username
     * @param credentials the password
     * @param authorities the list of {@link GrantedAuthority} objects
     * @return a new {@link UsernamePasswordToken} instance marked as authenticated
     */
    public static UsernamePasswordToken authenticated(String principal, String credentials, List<GrantedAuthority> authorities) {
        return new UsernamePasswordToken(principal, credentials, authorities);
    }

    /**
     * Creates an authenticated token based on a {@link BizUser} object.
     *
     * @param user the {@link BizUser} containing user details
     * @return a new {@link UsernamePasswordToken} instance marked as authenticated with user details
     */
    public static UsernamePasswordToken authenticated(BizUser user) {
        var authentication = new UsernamePasswordToken(user.username(), null, Collections.emptyList());
        authentication.setDetails(user);
        return authentication;
    }

    /**
     * Erases the credentials from the token for security purposes.
     */
    @Override
    public void eraseCredentials() {
        this.credentials = "";
    }
}