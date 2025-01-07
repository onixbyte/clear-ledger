package com.onixbyte.clearledger.security.provider;

import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UserAuthenticationToken;
import com.onixbyte.clearledger.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDaoAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserService userService;

    @Autowired
    public UserDaoAuthenticationProvider(UserService userService,
                                         PasswordEncoder passwordEncoder) {
        setUserDetailsService(userService);
        setPasswordEncoder(passwordEncoder);
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication instanceof UserAuthenticationToken authenticationToken){
            log.info("principle = {}, credentials = {}", authenticationToken.getPrincipal(), authenticationToken.getCredentials());

            var userDetails = userService.loadUserByUsername(authenticationToken.getPrincipal());
            if (getPasswordEncoder().matches(authenticationToken.getCredentials(), userDetails.getPassword())) {
                authenticationToken.setAuthenticated(true);
                authenticationToken.setDetails(userDetails);
                authenticationToken.setAuthorities(userDetails.getAuthorities());
                return authenticationToken;
            }

            throw new UnauthenticatedException("Password is incorrect.");
        }
        throw new UnauthenticatedException("Server error!");
    }

}
