package com.onixbyte.clearledger.security.provider;

import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import com.onixbyte.clearledger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final static Logger log = LoggerFactory.getLogger(UsernamePasswordAuthenticationProvider.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsernamePasswordAuthenticationProvider(UserService userService,
                                                  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication instanceof UsernamePasswordToken usernamePasswordToken) {
            log.info("Authenticating, principle = {}, credentials = {}", usernamePasswordToken.getPrincipal(), usernamePasswordToken.getCredentials());

            var userDetails = userService.loadUserByUsername(usernamePasswordToken.getPrincipal());
            if (passwordEncoder.matches(usernamePasswordToken.getCredentials(), userDetails.getPassword())) {
                usernamePasswordToken.eraseCredentials();
                usernamePasswordToken.setAuthenticated(true);
                usernamePasswordToken.setDetails(userDetails);
                usernamePasswordToken.setAuthorities(userDetails.getAuthorities());
                return usernamePasswordToken;
            }

            throw new UnauthenticatedException("用户名与密码不匹配");
        }
        throw new UnauthenticatedException("服务器错误，无法完成身份验证");
    }

}
