package com.singh.astha.notificationservice.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.singh.astha.notificationservice.dtos.response.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;

@ControllerAdvice
public class InvalidFormatExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleInvalidFormatException(HttpServletRequest httpServletRequest,
                                                                                InvalidFormatException exception) {
        HashMap<String, String> errors = new HashMap<>();
        if (exception.getTargetType().isEnum()) {
            String validValues = Arrays.toString(exception.getTargetType().getEnumConstants());
            errors.put(exception.getPath().get(0).getFieldName(), String.format("must we among %s", validValues));
        }
        return ResponseEntity.badRequest().body(ResponseWrapper.failure(errors));
    }
}
