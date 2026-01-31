package com.boilerplate.app.base.logging;

import com.boilerplate.app.base.constant.LoggingConstants;
import com.boilerplate.app.base.util.JsonFormatter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Logger for HTTP request and response details.
 * Used by ControllerLoggingInterceptor to log incoming requests and outgoing responses.
 */
@Slf4j
public class RequestResponseLogger {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RequestResponseLogger.class);

    public static void logRequest(
        String traceId,
        String method,
        String url,
        Map<String, String> headers,
        byte[] body
    ) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("method", method);
        requestData.put("url", url);
        if (headers != null && !headers.isEmpty()) {
            requestData.put("headers", headers);
        }
        
        if (body != null && body.length > 0) {
            try {
                String bodyString = new String(body, StandardCharsets.UTF_8);
                Object bodyObj = JsonFormatter.parseJson(bodyString);
                requestData.put("body", bodyObj);
            } catch (Exception e) {
                requestData.put("body", new String(body, StandardCharsets.UTF_8));
            }
        } else {
            requestData.put("body", null);
        }
        
        LoggingUtil.logInfo(logger, traceId, LoggingConstants.OPERATION_REQUEST, requestData, null);
    }

    public static void logResponse(
        String traceId,
        int status,
        Map<String, String> headers,
        byte[] body,
        long elapsedTime
    ) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", status);
        responseData.put("elapsedTime", elapsedTime + "ms");
        if (headers != null && !headers.isEmpty()) {
            responseData.put("headers", headers);
        }
        if (body != null && body.length > 0) {
            String bodyString = new String(body, StandardCharsets.UTF_8);
            Object bodyObj = JsonFormatter.parseJson(bodyString);
            responseData.put("body", bodyObj);
        }
        
        LoggingUtil.logInfo(logger, traceId, LoggingConstants.OPERATION_RESPONSE, responseData, String.valueOf(status));
    }
    

    public static void logError(
        String traceId,
        String method,
        String url,
        Exception ex,
        int status
    ) {
        Map<String, Object> errorData = new HashMap<>();
        errorData.put("method", method);
        errorData.put("url", url);
        errorData.put("exception", ex.getClass().getName());
        errorData.put("message", ex.getMessage());
        
        LoggingUtil.logError(logger, traceId, LoggingConstants.OPERATION_ERROR, errorData, String.valueOf(status), ex.getMessage(), ex);
    }
}

