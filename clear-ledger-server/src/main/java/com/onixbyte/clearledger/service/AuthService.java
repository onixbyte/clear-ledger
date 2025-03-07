package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.dto.BizUser;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.exception.UnauthenticatedException;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final RedisTemplate<String, BizUser> userCache;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService,
                       RedisTemplate<String, BizUser> userCache,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userCache = userCache;
        this.authenticationManager = authenticationManager;
    }

    public BizUser login(String username, String password) {
        try {
            // perform authentication
            var _auth = authenticationManager.authenticate(UsernamePasswordToken.unauthenticated(
                    username, password));
            if (_auth instanceof UsernamePasswordToken authentication) {
                var bizUser = authentication.getDetails();
                // save data to cache server for 1 day
                userCache.opsForValue().set(userService.composeKey(bizUser.username()), bizUser, Duration.ofDays(1));
                // compose response entity
                return bizUser;
            }

            throw new UnauthenticatedException("Server error!");
        } catch (AuthenticationException e) {
            throw new UnauthenticatedException();
        }
    }

    @Transactional
    public BizUser register(User user) {
        // ensure user can be created
        userService.saveUser(user);
        var bizUser = user.toBiz();
        // save data to cache server for 1 day
        userCache.opsForValue().set(userService.composeKey(user.getUsername()), bizUser, Duration.ofDays(1));
        return bizUser;
    }

}
