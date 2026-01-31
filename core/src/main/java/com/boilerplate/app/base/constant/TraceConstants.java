package com.boilerplate.app.base.constant;

/**
 * Constants related to tracing and request attributes.
 */
public final class TraceConstants {

    private TraceConstants() {
        // Utility class
    }

    // MDC key for trace ID
    public static final String TRACE_ID_KEY = "traceId";

    // Request attribute keys used by the logging interceptor
    public static final String REQUEST_ATTRIBUTE_TRACE_ID = "traceId";
    public static final String REQUEST_ATTRIBUTE_START_TIME = "startTime";
    public static final String REQUEST_ATTRIBUTE_CACHED_BODY = "cachedRequestBody";
}


