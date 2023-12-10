package com.singh.astha.notification.service.enums;

import com.singh.astha.notification.service.utils.Constants;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("001", "Internal Server Error"),
    NOT_FOUND("002", "Not Found"),
    INPUT_VALIDATION_ERROR("003", "Input Validation Error"),
    INVALID_JWT_TOKEN("004", "Invalid JWT token provided"),
    ;

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = Constants.ERROR_CODE_PREFIX + code;
        this.message = message;
    }
}
