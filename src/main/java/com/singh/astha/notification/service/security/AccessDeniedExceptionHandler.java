package com.singh.astha.notification.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notification.service.dtos.response.ResponseWrapper;
import com.singh.astha.notification.service.utils.StaticMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public AccessDeniedExceptionHandler(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        StaticMethods
                .writeResponse(response, HttpStatus.FORBIDDEN.value(), ResponseWrapper.failure(null), objectMapper);
    }
}
