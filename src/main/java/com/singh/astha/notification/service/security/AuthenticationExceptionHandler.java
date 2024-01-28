package com.singh.astha.notification.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singh.astha.notification.service.dtos.response.wrapper.ErrorResponse;
import com.singh.astha.notification.service.dtos.response.wrapper.ResponseWrapper;
import com.singh.astha.notification.service.enums.ErrorCode;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.security.service.JwtService;
import com.singh.astha.notification.service.security.utils.Constants;
import com.singh.astha.notification.service.security.utils.ErrorMessages;
import com.singh.astha.notification.service.utils.StaticMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    public AuthenticationExceptionHandler(JwtService jwtService) {
        this.jwtService = jwtService;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        response.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        try {
            String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
            ErrorResponse errorResponse = ErrorResponse
                    .from(ErrorCode.INPUT_VALIDATION_ERROR, ErrorMessages.AUTHORIZATION_HEADER_MUST_BE_PRESENT);
            if (authorizationHeader == null) {
                StaticMethods.writeResponse(
                        response,
                        HttpStatus.BAD_REQUEST,
                        ResponseWrapper.failure(List.of(errorResponse)),
                        objectMapper
                );
                return;
            }
            jwtService.verifyAndDecodeToken(authorizationHeader);
        } catch (ResponseException responseException) {
            ErrorResponse errorResponse = ErrorResponse
                    .from(ErrorCode.INVALID_JWT_TOKEN, responseException.getMessage());
            StaticMethods.writeResponse(
                    response,
                    responseException.getStatus(),
                    ResponseWrapper.failure(List.of(errorResponse)),
                    objectMapper
            );
        }
    }

}
