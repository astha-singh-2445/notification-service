package com.singh.astha.notification.service.exceptions;

import com.singh.astha.notification.service.dtos.response.wrapper.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ResponseException extends RuntimeException {

    private final HttpStatus status;

    private final List<ErrorResponse> errorResponses;

    public ResponseException(HttpStatus status, ErrorResponse errorResponse) {
        this(status, errorResponse, null);
    }

    public ResponseException(HttpStatus status, ErrorResponse errorResponse, Throwable cause) {
        this(status, List.of(errorResponse), cause);
    }

    public ResponseException(HttpStatus status, List<ErrorResponse> errorResponses) {
        this(status, errorResponses, null);
    }

    public ResponseException(HttpStatus status, List<ErrorResponse> errorResponses, Throwable cause) {
        super(cause);
        this.status = status;
        this.errorResponses = errorResponses;
    }
}
