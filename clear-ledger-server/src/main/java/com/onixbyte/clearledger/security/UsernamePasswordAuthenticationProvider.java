package com.onixbyte.clearledger.security;

import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import com.onixbyte.clearledger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * An authentication provider for username and password-based authentication.
 * <p>
 * Implements {@link AuthenticationProvider} to validate {@link UsernamePasswordToken} instances
 * against stored user credentials and assign authorities.
 *
 * @author zihluwang
 */
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final static Logger log = LoggerFactory.getLogger(UsernamePasswordAuthenticationProvider.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an authentication provider with required dependencies.
     *
     * @param userService     the service for retrieving user details
     * @param passwordEncoder the encoder for validating passwords
     */
    @Autowired
    public UsernamePasswordAuthenticationProvider(UserService userService,
                                                  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Indicates whether this provider supports the given authentication type.
     *
     * @param authentication the authentication class to check
     * @return true if the authentication is a {@link UsernamePasswordToken}, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordToken.class.isAssignableFrom(authentication);
    }

    /**
     * Authenticates a user based on the provided authentication token.
     * <p>
     * Validates the username and password against stored credentials, sets the authentication
     * details, and assigns authorities if successful.
     *
     * @param authentication the {@link Authentication} object to authenticate
     * @return the authenticated {@link Authentication} object
     * @throws BizException if authentication fails due to invalid credentials or server error
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication instanceof UsernamePasswordToken usernamePasswordToken) {
            log.info("Authenticating, principle = {}, credentials = {}", usernamePasswordToken.getPrincipal(), usernamePasswordToken.getCredentials());

            var userDetails = userService.loadUserByUsername(usernamePasswordToken.getPrincipal());
            if (passwordEncoder.matches(usernamePasswordToken.getCredentials(), userDetails.getPassword())) {
                var bizUser = userDetails.toBiz();
                usernamePasswordToken.eraseCredentials();
                usernamePasswordToken.setAuthenticated(true);
                usernamePasswordToken.setDetails(bizUser);
                usernamePasswordToken.setAuthorities(List.of(new SimpleGrantedAuthority("user")));
                return usernamePasswordToken;
            }

            throw BizException.unauthorised("Username and password do not match");
        }
        throw BizException.unauthorised("Server error, unable to complete authentication");
    }
}