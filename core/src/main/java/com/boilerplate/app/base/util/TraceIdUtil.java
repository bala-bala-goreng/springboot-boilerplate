package com.boilerplate.app.base.util;

import com.boilerplate.app.base.constant.TraceConstants;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Utility for managing trace IDs using SLF4J's MDC (thread-local storage).
 * Trace IDs are generated in preHandle() and cleared in afterCompletion().
 */
public class TraceIdUtil {

    /**
     * Generates a new UUID trace ID and stores it in MDC.
     * Should be called once at the start of each request (in interceptor preHandle()).
     */
    public static String generateAndSet() {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TraceConstants.TRACE_ID_KEY, traceId);
        return traceId;
    }

    /**
     * Retrieves trace ID from MDC. Generates a new one if not found.
     */
    public static String get() {
        String traceId = MDC.get(TraceConstants.TRACE_ID_KEY);
        if (traceId == null || traceId.isEmpty()) {
            traceId = generateAndSet();
        }
        return traceId;
    }

    /**
     * Manually sets a trace ID in MDC.
     * Useful for propagating trace ID from request headers or async contexts.
     */
    public static void set(String traceId) {
        if (traceId != null && !traceId.isEmpty()) {
            MDC.put(TraceConstants.TRACE_ID_KEY, traceId);
        }
    }

    /**
     * Removes trace ID from MDC.
     * Should be called at the end of each request (in interceptor afterCompletion()) to prevent memory leaks.
     */
    public static void clear() {
        MDC.remove(TraceConstants.TRACE_ID_KEY);
    }
}

