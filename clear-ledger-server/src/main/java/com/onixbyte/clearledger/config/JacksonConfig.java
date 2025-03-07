package com.onixbyte.clearledger.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.onixbyte.clearledger.utils.Formatters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class JacksonConfig {

    @Bean
    public SimpleModule javaTimeModule() {
        var module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(Formatters.DATE_TIME_FORMATTER));
        module.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(Formatters.DATE_TIME_FORMATTER));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(Formatters.DATE_FORMATTER));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(Formatters.DATE_FORMATTER));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(Formatters.TIME_FORMATTER));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(Formatters.TIME_FORMATTER));
        return module;
    }

}
