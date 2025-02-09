package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.entity.table.UserTableDef;
import com.onixbyte.clearledger.exception.BizException;
import com.onixbyte.clearledger.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDomain loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                .map(User::toDomain)
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

}
