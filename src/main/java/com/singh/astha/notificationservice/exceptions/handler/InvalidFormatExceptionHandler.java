package com.singh.astha.notificationservice.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.singh.astha.notificationservice.dtos.response.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
public class InvalidFormatExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleInvalidFormatException(HttpServletRequest httpServletRequest,
                                                                                InvalidFormatException exception) {
        ResponseWrapper<Object> responseWrapper = ResponseWrapper.failure(exception.getValue(),
                exception.getOriginalMessage());
        return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
    }
}
