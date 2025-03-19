package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.configuration.property.EmailProperty;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailProperty emailProperty;
    private final JavaMailSender mailSender;
    private final TemplateService templateService;

    public EmailService(EmailProperty emailProperty,
                        JavaMailSender mailSender,
                        TemplateService templateService) {
        this.emailProperty = emailProperty;
        this.mailSender = mailSender;
        this.templateService = templateService;
    }

    @Async
    public void sendPlainTextMessage(String audience, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        sendMessage(audience, subject, content, false);
    }

    @Async
    public void sendRichTextMessage(String audience, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        sendMessage(audience, subject, content, true);
    }

    @Async
    public void sendTemplatedRichTextMessage(String audience, String subject, String templateName, Map<String, Object> params) throws MessagingException, UnsupportedEncodingException {
        var htmlContent = templateService.process(templateName, params);
        sendMessage(audience, subject, htmlContent, true);
    }

    private void sendMessage(String audience, String subject, String content, boolean isRichText) throws MessagingException, UnsupportedEncodingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, isRichText, "UTF-8");
        helper.setFrom(emailProperty.getFrom(), emailProperty.getSender());
        helper.setTo(audience);
        helper.setSubject(subject);
        helper.setText(content, isRichText);

        mailSender.send(message);

        log.info("Email has been post to {}", audience);
    }

}
