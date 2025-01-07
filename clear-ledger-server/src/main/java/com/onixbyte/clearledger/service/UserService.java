package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.data.entity.table.UserTableDef;
import com.onixbyte.clearledger.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDomain loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.selectOneByCondition(UserTableDef.USER.USERNAME.eq(username)))
                .map(User::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with given username."));
    }


}
