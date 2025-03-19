package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.configuration.property.EmailProperty;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailProperty emailProperty;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(EmailProperty emailProperty,
                        JavaMailSender mailSender,
                        SpringTemplateEngine templateEngine) {
        this.emailProperty = emailProperty;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
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
    public void sendRichTextMessageWithTemplate(String audience, String subject, String templateName, IContext context) throws MessagingException, UnsupportedEncodingException {
        var htmlContent = templateEngine.process(templateName, context);
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
