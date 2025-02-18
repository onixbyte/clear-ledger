package com.onixbyte.clearledger.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public final class ResponseUtil {

    private final ObjectMapper objectMapper;

    @Autowired
    public ResponseUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> void writeResponse(HttpServletResponse response, HttpStatus status, T data)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(data));
        response.getWriter().flush();
    }

}
