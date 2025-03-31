package com.onixbyte.clearledger.service;

import com.onixbyte.clearledger.configuration.property.EmailProperty;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Service for sending emails to users.
 * <p>
 * This class provides methods to send plain text, rich text, and templated rich text emails
 * asynchronously using the configured email properties and a JavaMailSender.
 *
 * @author zihluwang
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailProperty emailProperty;
    private final JavaMailSender mailSender;
    private final TemplateService templateService;

    /**
     * Constructs an email service with required dependencies.
     *
     * @param emailProperty   the configuration properties for email settings
     * @param mailSender      the sender implementation for dispatching emails
     * @param templateService the service for processing email templates
     */
    @Autowired
    public EmailService(EmailProperty emailProperty,
                        JavaMailSender mailSender,
                        TemplateService templateService) {
        this.emailProperty = emailProperty;
        this.mailSender = mailSender;
        this.templateService = templateService;
    }

    /**
     * Sends a plain text email to the specified audience asynchronously.
     *
     * @param audience the recipient's email address
     * @param subject  the subject of the email
     * @param content  the plain text content of the email
     * @throws MessagingException           if an error occurs while preparing or sending the email
     * @throws UnsupportedEncodingException if the character encoding is unsupported
     */
    @Async
    public void sendPlainTextMessage(String audience, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        sendMessage(audience, subject, content, false);
    }

    /**
     * Sends a rich text (HTML) email to the specified audience asynchronously.
     *
     * @param audience the recipient's email address
     * @param subject  the subject of the email
     * @param content  the HTML content of the email
     * @throws MessagingException           if an error occurs while preparing or sending the email
     * @throws UnsupportedEncodingException if the character encoding is unsupported
     */
    @Async
    public void sendRichTextMessage(String audience, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        sendMessage(audience, subject, content, true);
    }

    /**
     * Sends a templated rich text (HTML) email to the specified audience asynchronously.
     * <p>
     * Processes the specified template with the provided parameters to generate the email content.
     *
     * @param audience     the recipient's email address
     * @param subject      the subject of the email
     * @param templateName the name of the template to process
     * @param params       the parameters to substitute into the template
     * @throws MessagingException           if an error occurs while preparing or sending the email
     * @throws UnsupportedEncodingException if the character encoding is unsupported
     */
    @Async
    public void sendTemplatedRichTextMessage(String audience, String subject, String templateName, Map<String, Object> params) throws MessagingException, UnsupportedEncodingException {
        var htmlContent = templateService.process(templateName, params);
        sendMessage(audience, subject, htmlContent, true);
    }

    /**
     * Sends an email with the specified details.
     * <p>
     * Configures and dispatches the email using the {@link JavaMailSender}, supporting both plain
     * text and rich text formats.
     *
     * @param audience   the recipient's email address
     * @param subject    the subject of the email
     * @param content    the content of the email (plain text or HTML)
     * @param isRichText indicates whether the content is HTML (true) or plain text (false)
     * @throws MessagingException           if an error occurs while preparing or sending the email
     * @throws UnsupportedEncodingException if the character encoding is unsupported
     */
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
