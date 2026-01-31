package com.boilerplate.app.exception;

import com.boilerplate.app.constant.ResponseErrorCodeEnum;
import com.boilerplate.app.model.dto.response.ResponseCodeResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice(basePackages = "com.boilerplate.app.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            @NonNull NoResourceFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return new ResponseEntity<>(
                new ResponseCodeResponseDto(
                        ResponseErrorCodeEnum.INVALID_URL.getCode(),
                        "Invalid URL"),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        BindingResult result = ex.getBindingResult();
        FieldError firstError = result.getFieldErrors().getFirst();
        String message = getMessage(firstError);

        return new ResponseEntity<>(
                new ResponseCodeResponseDto(
                        ResponseErrorCodeEnum.MALFORMED_BODY.getCode(),
                        message),
                HttpStatus.BAD_REQUEST);
    }

    private static String getMessage(FieldError firstError) {
        String constraint = firstError.getCode();
        String field = firstError.getField();
        String message;

        switch (constraint) {
            case "NotBlank", "NotNull", "NotEmpty" ->
                    message = String.format("Field {%s} is mandatory", field);
            case "DatePattern" ->
                    message = String.format("Field {%s} has invalid format", field);
            case null, default ->
                    message = "Bad Request";
        }
        return message;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        log.error("Failed to read request body: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ResponseCodeResponseDto(
                        ResponseErrorCodeEnum.MALFORMED_BODY.getCode(),
                        "Failed to read request: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseCodeResponseDto> handleCustomException(CustomException ex) {
        HttpStatusCode statusCode = ex.getStatus();
        return new ResponseEntity<>(
                new ResponseCodeResponseDto(ex.getCode(), ex.getMessage()),
                statusCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        return new ResponseEntity<>(
                new ResponseCodeResponseDto(
                        ResponseErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(),
                        "An unexpected error occurred."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

