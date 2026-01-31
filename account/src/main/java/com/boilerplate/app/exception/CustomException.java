package com.boilerplate.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus status;

    public CustomException(String caseCode, String message, HttpStatus status) {
        super(message);
        this.code = caseCode;
        this.message = message;
        this.status = status;
    }

    public CustomException(String caseCode, String message) {
        super(message);
        this.code = caseCode;
        this.message = message;
        this.status = HttpStatus.OK;
    }
}

