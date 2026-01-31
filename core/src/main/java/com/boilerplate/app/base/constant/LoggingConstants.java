package com.boilerplate.app.base.constant;

/**
 * Constants used for base logging across services.
 */
public final class LoggingConstants {

    private LoggingConstants() {
        // Utility class
    }

    // Default service name used when no explicit name is configured
    public static final String DEFAULT_SERVICE_NAME = "service";

    // Operation names for structured logging
    public static final String OPERATION_REQUEST = "REQUEST";
    public static final String OPERATION_RESPONSE = "RESPONSE";
    public static final String OPERATION_ERROR = "ERROR";
}


