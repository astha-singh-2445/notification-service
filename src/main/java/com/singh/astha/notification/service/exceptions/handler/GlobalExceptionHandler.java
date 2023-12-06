package com.singh.astha.notification.service.exceptions.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.singh.astha.notification.service.dtos.response.ResponseWrapper;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.utils.MessageConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseWrapper<Object>> handle(
            HttpServletRequest httpServletRequest,
            ResponseException exception
    ) {
        ResponseWrapper<Object> responseWrapper = ResponseWrapper.failure(
                exception.getPayload(),
                exception.getMessage()
        );
        return new ResponseEntity<>(responseWrapper, exception.getStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleInvalidFormatException(
            HttpServletRequest httpServletRequest,
            InvalidFormatException invalidFormatException
    ) {
        HashMap<String, String> errors = new HashMap<>();

        Class<?> targetType = invalidFormatException.getTargetType();
        if (targetType != null && targetType.isEnum()) {
            List<JsonMappingException.Reference> path = invalidFormatException.getPath();
            String fieldName = path.get(path.size() - 1).getFieldName();
            errors.put(
                    fieldName,
                    String.format(
                            MessageConstants.ENUM_INVALID_CAST_MESSAGE,
                            Arrays.toString(targetType.getEnumConstants())
                    )
            );
        }
        return ResponseEntity.badRequest().body(ResponseWrapper.failure(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleDuplicationKeyException(
            HttpServletRequest httpServletRequest,
            MethodArgumentNotValidException exception
    ) {
        HashMap<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(ResponseWrapper.failure(errors));
    }
}
