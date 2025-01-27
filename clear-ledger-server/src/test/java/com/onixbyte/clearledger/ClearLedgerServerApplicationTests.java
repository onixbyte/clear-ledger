package com.onixbyte.clearledger;

import com.onixbyte.clearledger.data.entity.User;
import com.onixbyte.clearledger.repository.UserRepository;
import com.onixbyte.guid.GuidCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
@ActiveProfiles({"dev"})
class ClearLedgerServerApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuidCreator<Long> userIdCreator;

    @Test
    @Transactional
    void contextLoads() {
        var user1 = User.builder()
                .id(userIdCreator.nextId())
                .username("test_user_1")
                .password(passwordEncoder.encode("123456"))
                .email("user1@clear-ledger.onixbyte.com")
                .createdAt(LocalDateTime.now())
                .build();
        var user2 = User.builder()
                .id(userIdCreator.nextId())
                .username("test_user_2")
                .password(passwordEncoder.encode("123456"))
                .email("user2@clear-ledger.onixbyte.com")
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.insertBatch(List.of(user1, user2));
    }

}
