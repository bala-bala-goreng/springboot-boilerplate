package com.boilerplate.app.base.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.boilerplate.app.base.constant.DateTimeConstants.ISO_OFFSET_DATE_TIME_MILLIS_PATTERN;

/**
 * Utility for date and time formatting operations.
 */
public class DateTimeUtil {
    
    public static String getDateTimeNowFormat() {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_OFFSET_DATE_TIME_MILLIS_PATTERN);
        return now.format(formatter);
    }
}
