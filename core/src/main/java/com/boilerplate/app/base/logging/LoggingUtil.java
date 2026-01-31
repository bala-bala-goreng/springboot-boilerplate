package com.boilerplate.app.base.logging;

import com.boilerplate.app.base.constant.LoggingConstants;
import com.boilerplate.app.base.util.JsonFormatter;
import lombok.Getter;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class LoggingUtil {

    @Getter
    private static volatile String serviceName = LoggingConstants.DEFAULT_SERVICE_NAME;

    public static void setServiceName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            serviceName = name;
        }
    }

    public static void logInfo(Logger logger, String traceId, String activity, Object message, String responseCode) {
        String className = extractClassNameFromLogger(logger);
        Map<String, Object> logData = buildLogData(traceId, className, activity, message, responseCode, null);
        String logMessage = formatAsJson(logData);
        logger.info(logMessage);
    }

    public static void logError(Logger logger, String traceId, String activity, Object message, String responseCode, String errorMessage) {
        String className = extractClassNameFromLogger(logger);
        Map<String, Object> logData = buildLogData(traceId, className, activity, message, responseCode, errorMessage);
        String logMessage = formatAsJson(logData);
        logger.error(logMessage);
    }

    public static void logError(Logger logger, String traceId, String activity, Object message, String responseCode, String errorMessage, Throwable throwable) {
        String className = extractClassNameFromLogger(logger);
        Map<String, Object> logData = buildLogData(traceId, className, activity, message, responseCode, errorMessage);

        if (throwable != null) {
            logData.put("exception.type", throwable.getClass().getName());
            logData.put("exception.message", throwable.getMessage());
            String stackTrace = getStackTrace(throwable);
            if (stackTrace != null) {
                logData.put("exception.stacktrace", stackTrace);
            }
        }
        
        String logMessage = formatAsJson(logData);
        logger.error(logMessage, throwable);
    }

    private static Map<String, Object> buildLogData(String traceId, String className, String activity, Object message, String responseCode, String errorMessage) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("trace_id", traceId != null ? traceId : "N/A");
        logData.put("timestamp", Instant.now().toString());
        logData.put("service.name", serviceName);
        logData.put("service.namespace", className);
        logData.put("operation.name", activity);

        if (message != null) {
            Object messageFormatted = formatJsonObject(message);
            logData.put("message", messageFormatted);
        }

        if (responseCode != null) {
            logData.put("response.code", responseCode);
        }

        if (errorMessage != null) {
            logData.put("error", true);
            logData.put("error.message", errorMessage);
        }

        logData.put("log.class", className);
        
        return logData;
    }

    private static String formatAsJson(Map<String, Object> logData) {
        try {
            return JsonFormatter.toPrettyJson(logData);
        } catch (Exception e) {
            return String.format("{\n  \"error\": \"Failed to serialize log data\",\n  \"trace_id\": \"%s\",\n  \"message\": \"%s\"\n}", 
                    logData.get("trace_id"), e.getMessage());
        }
    }

    private static Object formatJsonObject(Object data) {
        if (data == null) {
            return null;
        }

        if (data instanceof Map) {
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) data).entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                Object formattedValue = formatJsonObject(value);
                map.put(key.toString(), formattedValue);
            }
            return map;
        }

        if (data instanceof java.util.Collection) {
            java.util.List<Object> list = new java.util.ArrayList<>();
            for (Object item : (java.util.Collection<?>) data) {
                list.add(formatJsonObject(item));
            }
            return list;
        }

        if (data instanceof String) {
            return JsonFormatter.parseJson((String) data);
        }

        try {
            String jsonString = JsonFormatter.toJson(data);
            return JsonFormatter.parseJson(jsonString);
        } catch (Exception e) {
            // Fallback to string representation
            return data.toString();
        }
    }

    private static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int maxLines = Math.min(stackTrace.length, 10); // Limit to 10 lines
        
        for (int i = 0; i < maxLines; i++) {
            sb.append(stackTrace[i].toString());
            if (i < maxLines - 1) {
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }

    private static String extractClassNameFromLogger(Logger logger) {
        String loggerName = logger.getName();
        int lastDot = loggerName.lastIndexOf('.');
        return lastDot >= 0 ? loggerName.substring(lastDot + 1) : loggerName;
    }
}

