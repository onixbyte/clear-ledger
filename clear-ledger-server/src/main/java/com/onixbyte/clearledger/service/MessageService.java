package com.onixbyte.clearledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service class demonstrating the use of localized messages.
 *
 * @author zihluwang
 */
@Service
public class MessageService {

    private final MessageSource messageSource;

    @Autowired
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Retrieves a message with given message code and locale.
     *
     * @param messageName the name of the user
     * @param locale      the locale to use for the message
     * @return the localized welcome message
     */
    public String getMessage(String messageName, Locale locale) {
        return messageSource.getMessage(messageName, null, locale);
    }

    /**
     * Retrieves a message with given message code, arguments and locale.
     *
     * @param messageName the name of the user
     * @param args        arguments used to format the message
     * @param locale      the locale to use for the message
     * @return the localized welcome message
     */
    public String getMessage(String messageName, Object[] args, Locale locale) {
        return messageSource.getMessage(messageName, args, locale);
    }

}
