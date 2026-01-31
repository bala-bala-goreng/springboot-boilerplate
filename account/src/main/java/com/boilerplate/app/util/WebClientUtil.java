package com.boilerplate.app.util;

import com.boilerplate.app.model.dto.response.ResponseCodeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class WebClientUtil {

    public static <T> Mono<Object> handleResponse(ClientResponse response, Class<T> responseType) {
        HttpStatusCode statusCode = response.statusCode();
        HttpStatus status = HttpStatus.resolve(statusCode.value());
        
        if (status == null) {
            return Mono.error(new RuntimeException("Unexpected status: " + statusCode));
        }

        if (status.is2xxSuccessful()) {
            return response.bodyToMono(responseType);
        } else if (status.is4xxClientError() || status.is5xxServerError()) {
            return response.bodyToMono(ResponseCodeResponseDto.class);
        } else {
            return Mono.error(new RuntimeException("Unexpected status: " + status));
        }
    }
}

