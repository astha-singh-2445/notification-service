package com.singh.astha.notification.service.dtos.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.singh.astha.notification.service.enums.ErrorCode;
import lombok.Builder;

import java.io.Serializable;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String errorCode, String message, String detail, String help) implements Serializable {

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse from(ErrorCode errorCode, String detail) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .detail(detail)
                .build();
    }

    public static ErrorResponse from(ErrorCode errorCode, String detail, String help) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .detail(detail)
                .help(help)
                .build();
    }

}
