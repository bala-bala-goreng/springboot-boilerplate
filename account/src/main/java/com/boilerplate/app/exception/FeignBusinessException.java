package com.boilerplate.app.exception;

import com.boilerplate.app.constant.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class FeignBusinessException extends RuntimeException {
    private final ResponseCodeEnum responseCode;
    private final int httpStatus;

    public FeignBusinessException(ResponseCodeEnum responseCode, int httpStatus, String message) {
        super(message);
        this.responseCode = responseCode;
        this.httpStatus = httpStatus;
    }
}

