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
    INVALID_TOKEN("005", "Invalid Token"),
    AUTHORIZATION_HEADER_MUST_NOT_BE_NULL_AND_MUST_START_BE_BEARER(
            "006", "Authorization Header must not be null and must start be Bearer"
    ),
    USER_TOKEN_NOT_EXIST("007", "User Token does not exist"),
    TEMPLATE_IS_NOT_PRESENT("008", "Template is not present"),
    TEMPLATE_ALREADY_EXISTS("009", "Template already exists"),
    TEMPLATE_NOT_EXIST("010", "Template does not exist"),
    TOKEN_ALREADY_EXISTS("011", "Token already exists");

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = Constants.ERROR_CODE_PREFIX + code;
        this.message = message;
    }
}
