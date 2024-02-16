package com.singh.astha.notification.service.exceptions.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.singh.astha.notification.service.dtos.response.wrapper.ErrorResponse;
import com.singh.astha.notification.service.dtos.response.wrapper.ResponseWrapper;
import com.singh.astha.notification.service.enums.ErrorCode;
import com.singh.astha.notification.service.exceptions.ResponseException;
import com.singh.astha.notification.service.utils.Constants;
import com.singh.astha.notification.service.utils.MessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseWrapper<Object>> handle(
            ResponseException exception
    ) {
        ResponseWrapper<Object> responseWrapper = ResponseWrapper.failure(exception.getErrorResponses());
        return new ResponseEntity<>(responseWrapper, exception.getStatus());
    }

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleNoHandlerFoundException() {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseWrapper.failure(List.of(errorResponse)));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleInvalidFormatException(
            InvalidFormatException invalidFormatException
    ) {
        List<ErrorResponse> errorResponses = new LinkedList<>();
        // FIXME: Add payload for key identification
        Class<?> targetType = invalidFormatException.getTargetType();
        if (targetType != null && targetType.isEnum()) {
            HashMap<String, String> errors = new HashMap<>();
            List<JsonMappingException.Reference> path = invalidFormatException.getPath();
            String fieldName = path.get(path.size() - 1).getFieldName();
            errors.put(
                    fieldName,
                    String.format(
                            MessageConstants.ENUM_INVALID_CAST_MESSAGE,
                            Arrays.toString(targetType.getEnumConstants())
                    )
            );
            errors.forEach((key, value) -> {
                ErrorResponse errorResponse = ErrorResponse.from(
                        ErrorCode.INPUT_VALIDATION_ERROR,
                        String.format(Constants.KEY_VALUE_ERROR_FORMAT, key, value)
                );
                errorResponses.add(errorResponse);
            });
        }
        return ResponseEntity.badRequest().body(ResponseWrapper.failure(errorResponses));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleDuplicationKeyException(
            MethodArgumentNotValidException exception
    ) {
        // FIXME: Handle the errors other than FieldError as well
        HashMap<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        List<ErrorResponse> errorResponses = errors.entrySet()
                .stream()
                .map(
                        entry -> ErrorResponse.from(
                                ErrorCode.INPUT_VALIDATION_ERROR,
                                String.format(Constants.KEY_VALUE_ERROR_FORMAT, entry.getKey(), entry.getValue())
                        )
                )
                .toList();
        return ResponseEntity.badRequest().body(ResponseWrapper.failure(errorResponses));
    }
}
