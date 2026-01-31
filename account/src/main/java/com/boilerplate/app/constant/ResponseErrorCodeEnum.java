package com.boilerplate.app.constant;

import lombok.Getter;

@Getter
public enum ResponseErrorCodeEnum {
    // HTTP Status Codes 400
    MALFORMED_BODY("E003"),

    // HTTP Status Codes 401
    INVALID_ACCESS_TOKEN("E011"),
    INVALID_SIGNATURE("E012"),
    CLIENT_AUTHENTICATION_NEEDED("E013"),

    // HTTP Status Codes 500
    UNABLE_PARSE_MESSAGE("E002"),

    // HTTP Status Codes 500
    INTERNAL_SERVER_ERROR("E001"),

    // HTTP Status Codes 404
    INVALID_URL("E004");

    private final String code;

    ResponseErrorCodeEnum(String code) {
        this.code = code;
    }
}

