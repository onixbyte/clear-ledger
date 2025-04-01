package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.dto.BizUser;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.entity.table.UserTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.repository.UserRepository;
import com.onixbyte.clearledger.util.CacheKeyComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing user-related operations.
 * <p>
 * Provides methods for user registration, retrieval, and validation, with caching and password
 * encryption support, as well as a scheduled task to reset user serial numbers daily.
 *
 * @author zihluwang
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, BizUser> userCache;
    private final SerialService serialService;
    private final CacheKeyComposer cacheKeyComposer;

    /**
     * Constructs a user service with required dependencies.
     *
     * @param userRepository   the repository for user data operations
     * @param passwordEncoder  the encoder for securing user passwords
     * @param userCache        the Redis template for caching {@link BizUser} objects
     * @param serialService    the service for managing serial numbers
     * @param cacheKeyComposer the utility for composing cache keys
     */
    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RedisTemplate<String, BizUser> userCache,
                       SerialService serialService,
                       CacheKeyComposer cacheKeyComposer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCache = userCache;
        this.serialService = serialService;
        this.cacheKeyComposer = cacheKeyComposer;
    }

    /**
     * Loads a user by their username from the database.
     *
     * @param username the username of the user to load
     * @return the {@link User} entity
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with given username."));
    }

    /**
     * Checks whether the specified username is already in use.
     *
     * @param username the username to check
     * @return {@code true} if the username is taken, otherwise {@code false}
     */
    public boolean isUsernameTaken(String username) {
        return userRepository.selectCountByCondition(UserTableDef.USER.USERNAME.eq(username)) != 0;
    }

    /**
     * Checks whether the specified email is already in use.
     *
     * @param email the email address to check
     * @return {@code true} if the email is taken, otherwise {@code false}
     */
    public boolean isEmailTaken(String email) {
        return userRepository.selectCountByCondition(UserTableDef.USER.EMAIL.eq(email)) != 0;
    }

    /**
     * Saves a new user to the database with encrypted password.
     * <p>
     * Validates that the username and email are unique, encrypts the password, and persists the user
     * within a transaction.
     *
     * @param user the {@link User} entity to save
     * @return the number of affected rows (typically 1)
     * @throws BizException if the username or email is already taken
     */
    @Transactional
    public int saveUser(User user) {
        // check user can be registered
        if (isUsernameTaken(user.getUsername())) {
            throw BizException.conflict("Username is taken.");
        }

        if (isEmailTaken(user.getEmail())) {
            throw BizException.conflict("Email is taken.");
        }

        // encrypt user password
        var password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        // perform register
        return userRepository.insert(user);
    }

    /**
     * Retrieves a user by their username, checking the cache first.
     * <p>
     * Attempts to fetch the user from Redis cache; if not found, queries the database, caches the
     * result, and returns the business representation of the user.
     *
     * @param username the username of the user to retrieve
     * @return the {@link BizUser} object
     * @throws BizException if the user does not exist
     */
    public BizUser getUserByUsername(String username) {
        var userKey = cacheKeyComposer.getUserKey(username);
        return Optional.ofNullable(userCache.opsForValue().get(userKey))
                .or(() -> Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                        .map((_user) -> {
                            var bizUser = _user.toBiz();
                            userCache.opsForValue().set(userKey, bizUser);
                            return bizUser;
                        })
                )
                .orElseThrow(() -> BizException.unauthorised("用户不存在，请注册后再试。"));
    }

    /**
     * Resets the serial number for user-related operations daily.
     * <p>
     * Scheduled to run at midnight every day to reset the user serial counter.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void resetUserSerial() {
        log.info("Resetting user serial.");
        serialService.resetSerial("user");
        log.info("User serial has been reset to 0.");
    }

}
