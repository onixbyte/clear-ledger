package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.dto.BizUser;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.security.token.UsernamePasswordToken;
import com.onixbyte.clearledger.util.CacheKeyComposer;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Duration;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final RedisTemplate<String, BizUser> userCache;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeService verificationCodeService;
    private final RedisTemplate<String, String> verificationCodeCache;
    private final CacheKeyComposer cacheKeyComposer;

    public AuthService(UserService userService,
                       RedisTemplate<String, BizUser> userCache,
                       AuthenticationManager authenticationManager,
                       VerificationCodeService verificationCodeService,
                       RedisTemplate<String, String> verificationCodeCache,
                       CacheKeyComposer cacheKeyComposer) {
        this.userService = userService;
        this.userCache = userCache;
        this.authenticationManager = authenticationManager;
        this.verificationCodeService = verificationCodeService;
        this.verificationCodeCache = verificationCodeCache;
        this.cacheKeyComposer = cacheKeyComposer;
    }

    public BizUser login(String username, String password) {
        try {
            // perform authentication
            var _auth = authenticationManager.authenticate(UsernamePasswordToken.unauthenticated(
                    username, password));
            if (_auth instanceof UsernamePasswordToken authentication) {
                var bizUser = authentication.getDetails();
                // save data to cache server for 1 day
                userCache.opsForValue().set(cacheKeyComposer.getUserKey(bizUser.username()),
                        bizUser, Duration.ofDays(1));
                // compose response entity
                return bizUser;
            }

            throw BizException.unauthorised("Server error!");
        } catch (AuthenticationException e) {
            throw BizException.unauthorised("Server error");
        }
    }

    @Transactional
    public BizUser register(User user) {
        // ensure user can be created
        userService.saveUser(user);
        var bizUser = user.toBiz();
        // save data to cache server for 1 day
        userCache.opsForValue().set(cacheKeyComposer.getUserKey(user.getUsername()),
                bizUser, Duration.ofDays(1));
        return bizUser;
    }

    public void sendVerificationCode(String audience) {
        var lockKey = cacheKeyComposer.getVerificationLockKey(audience);
        var codeKey = cacheKeyComposer.getVerificationCodeKey(audience);
        try {
            if (!verificationCodeCache.hasKey(lockKey)) {
                // send audience, and get the verification code
                var code = verificationCodeService.sendVerificationMail(audience);

                verificationCodeCache.opsForValue().set(lockKey, "1", Duration.ofMinutes(1));
                verificationCodeCache.opsForValue().set(codeKey, code, Duration.ofMinutes(5));
            } else {
                throw BizException.tooManyRequests("您的请求频率过高，请稍后再试");
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw BizException.internalServerError("服务器异常，请稍后再试");
        }
    }

    public String getVerificationCode(String audience) {
        return verificationCodeCache.opsForValue()
                .get(cacheKeyComposer.getVerificationCodeKey(audience));
    }

}
