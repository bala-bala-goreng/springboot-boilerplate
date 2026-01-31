package com.boilerplate.app.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import static com.boilerplate.app.base.constant.DateTimeConstants.MONTH_DAY_PATTERN;

/**
 * Common utility methods for object conversion, JSON formatting, and date manipulation.
 */
public class CommonUtil {
    public static String objectToStringPretty(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static String objectToString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T convertObject(Object obj, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, clazz);
    }

    public static <T> T convertObject(String value, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String getYear(String date) {
        if (date.length() > 4) date = date.substring(0, 4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_DAY_PATTERN);
        MonthDay monthDay = MonthDay.parse(date, formatter);
        LocalDate now = LocalDate.now();

        if (monthDay.isAfter(MonthDay.of(now.getMonth(), now.getDayOfMonth()))) {
            return String.valueOf(now.getYear() - 1);
        } else {
            return String.valueOf(now.getYear());
        }
    }

    public static String addYear(String date) {
        return getYear(date) + date;
    }


    public static String getLastCharacter(String data, int length) {
        if (data == null || data.isEmpty()) {
            return "";
        }

        if (length > data.length()) {
            return data;
        }

        return data.substring(data.length() - length);
    }

    public static String minifyJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            return null;
        }
    }
}

