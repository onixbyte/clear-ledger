package com.onixbyte.clearledger.service;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;

@Service
public class VerificationCodeService {

    private static final Logger log = LoggerFactory.getLogger(VerificationCodeService.class);
    private final EmailService emailService;

    public VerificationCodeService(EmailService emailService) {
        this.emailService = emailService;
    }

    public String generateRandomVerificationCode() {
        final var random = new Random();
        var verificationCode = 100_000 + random.nextInt(900_000);
        return String.valueOf(verificationCode);
    }

    public String sendVerificationMail(String audience) throws MessagingException, UnsupportedEncodingException {
        var code = generateRandomVerificationCode();
        var params = new HashMap<String, Object>();
        params.put("verificationCode", code);
        emailService.sendTemplatedRichTextMessage(audience, "[Clear Ledger] 验证码", "verification-code", params);
        return code;
    }

}
