package com.singh.astha.notificationservice.exceptions.handler;

import com.singh.astha.notificationservice.dtos.response.ResponseWrapper;
import com.singh.astha.notificationservice.exceptions.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseWrapper<Object>> handle(HttpServletRequest httpServletRequest,
                                                          ResponseException exception) {
        ResponseWrapper<Object> responseWrapper = ResponseWrapper.failure(exception.getPayload(),
                exception.getMessage());
        return new ResponseEntity<>(responseWrapper, exception.getStatus());
    }
}
