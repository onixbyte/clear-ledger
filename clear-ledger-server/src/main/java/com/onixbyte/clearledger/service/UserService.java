package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.biz.BizUser;
import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.entity.table.UserTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final String appName;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, BizUser> userCache;

    @Autowired
    public UserService(@Value("${spring.application.name}") String appName,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RedisTemplate<String, BizUser> userCache) {
        this.appName = appName;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userCache = userCache;
    }

    public UserDomain loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                .map(User::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with given username."));
    }

    public String composeKey(String username) {
        return "%s:user:%s".formatted(appName, username);
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
        return Optional.ofNullable(userCache.opsForValue().get(composeKey(username)))
                .or(() -> Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                        .map((_user) -> {
                            var bizUser = _user.toBiz();
                            userCache.opsForValue().set(composeKey(bizUser.username()), bizUser);
                            return bizUser;
                        })
                )
                .orElseThrow(() -> new BizException(HttpStatus.UNAUTHORIZED, "用户不存在，请注册后再试。"));
    }

}
