package com.onixbyte.clearledger.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for email settings.
 * <p>
 * This class defines properties to configure email settings for a Spring Boot application. These
 * properties are bound to the {@code app.email} prefix in the application's configuration files
 * (e.g., {@code application.yml} or {@code application.properties}). It provides a structured way
 * to manage email-related configurations, such as the sender's address and display name.
 *
 * @author zihluwang
 */
@ConfigurationProperties(prefix = "app.email")
public class EmailProperty {

    /**
     * The email address used as the sender for outgoing emails.
     */
    private String sender;

    /**
     * The display name or email address shown in the "From" field of outgoing emails.
     */
    private String from;

    /**
     * Retrieves the sender's email address.
     *
     * @return the email address used as the sender, or {@code null} if not set
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender's email address.
     *
     * @param sender the email address to be used as the sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Retrieves the "From" field value for outgoing emails.
     *
     * @return the display name or email address for the "From" field, or {@code null} if not set
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the "From" field value for outgoing emails.
     *
     * @param from the display name or email address to be shown in the "From" field
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Default constructor for creating an instance of email properties.
     */
    public EmailProperty() {
    }
}