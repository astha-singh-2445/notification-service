package com.singh.astha.notification.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notification.service.dtos.response.ResponseWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class StaticMethods {
    private StaticMethods() {
    }

    public static <T> void writeResponse(
            HttpServletResponse response,
            int httpStatus,
            ResponseWrapper<T> responseWrapper,
            ObjectMapper objectMapper
    ) throws IOException {
        response.setStatus(httpStatus);
        response.getWriter().println(objectMapper.writeValueAsString(responseWrapper));
    }
}
