package com.boilerplate.app.base.logging;

import com.boilerplate.app.base.constant.LoggingConstants;
import com.boilerplate.app.base.util.JsonFormatter;
import com.boilerplate.app.base.util.TraceIdUtil;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Base Feign logging logger for structured logging of Feign client requests and responses.
 * Uses trace IDs from MDC (set by controller interceptor) for request correlation.
 * 
 * Usage in Feign configuration:
 * <pre>
 * public class MyFeignConfig {
 *     @Bean
 *     public Logger feignLogger() {
 *         return new FeignLoggingLogger();
 *     }
 *     
 *     @Bean
 *     public Logger.Level feignLoggerLevel() {
 *         return Logger.Level.FULL;
 *     }
 * }
 * </pre>
 */
public class FeignLoggingLogger extends Logger {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignLoggingLogger.class);

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        // Get trace ID from MDC (set by controller interceptor)
        String traceId = TraceIdUtil.get();
        
        // Log only the request body (DTO)
        Object requestDto = null;
        if (request.body() != null && request.body().length > 0) {
            String bodyString = new String(request.body(), StandardCharsets.UTF_8);
            requestDto = JsonFormatter.parseJson(bodyString);
        }
        
        LoggingUtil.logInfo(logger, traceId, LoggingConstants.OPERATION_REQUEST, requestDto, null);
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        // Get trace ID from MDC (set by controller interceptor)
        String traceId = TraceIdUtil.get();
        
        // Read response body
        byte[] bodyBytes = response.body() != null ? Util.toByteArray(response.body().asInputStream()) : null;
        
        // Log only the response body (DTO)
        Object responseDto = null;
        if (bodyBytes != null && bodyBytes.length > 0) {
            String bodyString = new String(bodyBytes, StandardCharsets.UTF_8);
            responseDto = JsonFormatter.parseJson(bodyString);
        }
        
        LoggingUtil.logInfo(logger, traceId, LoggingConstants.OPERATION_RESPONSE, responseDto, String.valueOf(response.status()));
        
        // Recreate response with buffered body
        if (bodyBytes != null) {
            return response.toBuilder().body(bodyBytes).build();
        }
        return response;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
    }
}

