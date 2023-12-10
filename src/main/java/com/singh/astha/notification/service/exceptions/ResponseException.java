package com.singh.astha.notification.service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseException extends RuntimeException {

    private final HttpStatus status;

    private final String message;

    public ResponseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
