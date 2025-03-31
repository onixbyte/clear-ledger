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

/**
 * Service for handling user authentication and registration operations.
 * <p>
 * This class provides methods for user login, registration, and verification code management,
 * integrating with authentication, caching, and email services.
 *
 * @author zihluwang
 */
@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserService userService;
    private final RedisTemplate<String, BizUser> userCache;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeService verificationCodeService;
    private final RedisTemplate<String, String> verificationCodeCache;
    private final CacheKeyComposer cacheKeyComposer;

    /**
     * Constructs an authentication service with required dependencies.
     *
     * @param userService            the service for managing user data
     * @param userCache              the Redis template for caching {@link BizUser} objects
     * @param authenticationManager  the manager for performing authentication
     * @param verificationCodeService the service for sending verification codes
     * @param verificationCodeCache  the Redis template for caching verification codes
     * @param cacheKeyComposer       the utility for composing cache keys
     */
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

    /**
     * Authenticates a user based on their username and password.
     * <p>
     * Performs authentication using the {@link AuthenticationManager}, caches the authenticated
     * user in Redis for one day, and returns the user's business representation.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user
     * @return the authenticated {@link BizUser} object
     * @throws BizException if authentication fails or a server error occurs
     */
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

    /**
     * Registers a new user and caches their details.
     * <p>
     * Saves the user to the database within a transaction, converts the entity to a business
     * representation, and caches it in Redis for one day.
     *
     * @param user the {@link User} entity to register
     * @return the registered {@link BizUser} object
     */
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

    /**
     * Sends a verification code to the specified audience.
     * <p>
     * Generates and sends a verification code via email, caching it in Redis with a five-minute
     * expiry. Implements a one-minute lock to prevent frequent requests.
     *
     * @param audience the recipient (e.g., email address) to send the verification code to
     * @throws BizException if the request rate is too high or a server error occurs
     */
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
                throw BizException.tooManyRequests(
                        "Your request frequency is too high, please try again later");
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw BizException.internalServerError("Server exception, please try again later");
        }
    }

    /**
     * Retrieves the cached verification code for the specified audience.
     *
     * @param audience the recipient (e.g., email address) associated with the verification code
     * @return the verification code, or null if not found in the cache
     */
    public String getVerificationCode(String audience) {
        return verificationCodeCache.opsForValue()
                .get(cacheKeyComposer.getVerificationCodeKey(audience));
    }
}