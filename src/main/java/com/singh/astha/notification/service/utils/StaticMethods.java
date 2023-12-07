package com.singh.astha.notification.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notification.service.dtos.response.wrapper.ResponseWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public final class StaticMethods {
    private StaticMethods() {
    }

    public static <T> void writeResponse(
            HttpServletResponse response,
            HttpStatus httpStatus,
            ResponseWrapper<T> responseWrapper,
            ObjectMapper objectMapper
    ) throws IOException {
        response.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        response.setStatus(httpStatus.value());
        response.getWriter().println(objectMapper.writeValueAsString(responseWrapper));
    }
}
