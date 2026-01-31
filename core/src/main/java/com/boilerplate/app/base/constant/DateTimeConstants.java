package com.boilerplate.app.base.constant;

/**
 * Date/time related constants used in the base package.
 */
public final class DateTimeConstants {

    private DateTimeConstants() {
        // Utility class
    }

    /**
     * Pattern for month-day format used in card expiry utilities.
     * Example: MMdd
     */
    public static final String MONTH_DAY_PATTERN = "MMdd";

    /**
     * ISO-8601 offset date-time pattern with milliseconds.
     * Example: 2025-12-23T09:05:53.153+07:00
     */
    public static final String ISO_OFFSET_DATE_TIME_MILLIS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
}


