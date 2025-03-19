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
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, BizUser> userCache;
    private final SerialService serialService;
    private final CacheKeyComposer cacheKeyComposer;

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

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with given username."));
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.selectCountByCondition(UserTableDef.USER.USERNAME.eq(username)) != 0;
    }

    public boolean isEmailTaken(String email) {
        return userRepository.selectCountByCondition(UserTableDef.USER.EMAIL.eq(email)) != 0;
    }

    @Transactional
    public int saveUser(User user) {
        // check user can be registered
        if (isUsernameTaken(user.getUsername())) {
            throw new BizException(HttpStatus.CONFLICT, "Username is taken.");
        }

        if (isEmailTaken(user.getEmail())) {
            throw new BizException(HttpStatus.CONFLICT, "Email is taken.");
        }

        // encrypt user password
        var password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        // perform register
        return userRepository.insert(user);
    }

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
                .orElseThrow(() -> new BizException(HttpStatus.UNAUTHORIZED, "用户不存在，请注册后再试。"));
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetUserSerial() {
        log.info("Resetting user serial.");
        serialService.resetSerial("user");
        log.info("User serial has been reset to 0.");
    }

}
