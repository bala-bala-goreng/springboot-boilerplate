package com.boilerplate.app.util;

import com.boilerplate.app.constant.DateFormatEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    public boolean isDateFormatValid(String input, DateFormatEnum formatEnum) {
        String format = formatEnum.getFormat();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        try {
            // Use custom parsing here for formats that are not supported by the default parseDate logic
            if (formatEnum == DateFormatEnum.YYYY_MM_DD_T_HH_MM_SS_SSSTZD) {
                OffsetDateTime.parse(input, formatter);
            } else {
                parseDate(input, format);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Default date parsing logic used for most simple formats.
     * This method does not support advanced patterns like offset timezones or optional milliseconds.
     */
    private void parseDate(String input, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                .withResolverStyle(ResolverStyle.STRICT);
        TemporalAccessor accessor = formatter.parse(input);

        int year = accessor.isSupported(ChronoField.YEAR) ? accessor.get(ChronoField.YEAR) : 2000;
        int month = accessor.isSupported(ChronoField.MONTH_OF_YEAR) ? accessor.get(ChronoField.MONTH_OF_YEAR) : 1;
        int day = accessor.isSupported(ChronoField.DAY_OF_MONTH) ? accessor.get(ChronoField.DAY_OF_MONTH) : 1;
        int hour = accessor.isSupported(ChronoField.HOUR_OF_DAY) ? accessor.get(ChronoField.HOUR_OF_DAY) : 0;
        int minute = accessor.isSupported(ChronoField.MINUTE_OF_HOUR) ? accessor.get(ChronoField.MINUTE_OF_HOUR) : 0;
        int second = accessor.isSupported(ChronoField.SECOND_OF_MINUTE) ? accessor.get(ChronoField.SECOND_OF_MINUTE) : 0;

        LocalDateTime.of(year, month, day, hour, minute, second);
    }
}

