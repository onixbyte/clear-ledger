package com.onixbyte.clearledger.service;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;

/**
 * Service for generating and sending verification codes via email.
 * <p>
 * Provides functionality to create random six-digit verification codes and dispatch them to users
 * using templated email messages.
 *
 * @author zihluwang
 */
@Service
public class VerificationCodeService {

    private static final Logger log = LoggerFactory.getLogger(VerificationCodeService.class);
    private final EmailService emailService;

    /**
     * Constructs a verification code service with the required email service dependency.
     *
     * @param emailService the service for sending emails
     */
    @Autowired
    public VerificationCodeService(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Generates a random six-digit verification code.
     * <p>
     * Produces a number between 100,000 and 999,999 using a random generator.
     *
     * @return the generated verification code as a string
     */
    public String generateRandomVerificationCode() {
        final var random = new Random();
        var verificationCode = 100_000 + random.nextInt(900_000);
        return String.valueOf(verificationCode);
    }

    /**
     * Sends a verification code to the specified audience via email.
     * <p>
     * Generates a random verification code, embeds it in a templated rich text email, and sends it
     * using the {@link EmailService}.
     *
     * @param audience the recipient's email address
     * @return the generated verification code
     * @throws MessagingException           if an error occurs while preparing or sending the email
     * @throws UnsupportedEncodingException if the character encoding is unsupported
     */
    public String sendVerificationMail(String audience) throws MessagingException, UnsupportedEncodingException {
        var code = generateRandomVerificationCode();
        var params = new HashMap<String, Object>();
        params.put("verificationCode", code);
        emailService.sendTemplatedRichTextMessage(audience, "[Clear Ledger] 验证码", "verification-code", params);
        return code;
    }

}
