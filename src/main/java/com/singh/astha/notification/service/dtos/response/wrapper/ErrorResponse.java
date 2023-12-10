package com.singh.astha.notification.service.dtos.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.singh.astha.notification.service.enums.ErrorCode;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    private String errorCode;

    private String message;

    private String detail;

    private String help;

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
