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
                var bizUser = userDetails.toBiz();
                usernamePasswordToken.eraseCredentials();
                usernamePasswordToken.setAuthenticated(true);
                usernamePasswordToken.setDetails(bizUser);
                usernamePasswordToken.setAuthorities(List.of(new SimpleGrantedAuthority("user")));
                return usernamePasswordToken;
            }

            throw BizException.unauthorised("用户名与密码不匹配");
        }
        throw BizException.unauthorised("服务器错误，无法完成身份验证");
    }

}
