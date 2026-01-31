package com.boilerplate.app.base.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Simple JSON formatter utility using Gson
 * Converts JSON strings to pretty-printed format and parses nested JSON strings
 */
public class JsonFormatter {
    
    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();
    
    private static final Gson simpleGson = new Gson();

    /**
     * Parse JSON string to object (recursively handles nested JSON strings)
     */
    public static Object parseJson(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return jsonString;
        }
        
        String trimmed = jsonString.trim();
        if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
            return jsonString; // Not JSON, return as string
        }
        
        try {
            JsonElement element = JsonParser.parseString(jsonString);
            return parseJsonElement(element);
        } catch (Exception e) {
            return jsonString; // If parsing fails, return original string
        }
    }
    
    /**
     * Recursively parse JsonElement, handling nested JSON strings
     */
    private static Object parseJsonElement(JsonElement element) {
        if (element.isJsonObject()) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            element.getAsJsonObject().entrySet().forEach(entry -> {
                JsonElement value = entry.getValue();
                if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
                    String str = value.getAsString();
                    // Check if string contains JSON
                    Object parsed = parseJson(str);
                    map.put(entry.getKey(), parsed);
                } else {
                    map.put(entry.getKey(), parseJsonElement(value));
                }
            });
            return map;
        } else if (element.isJsonArray()) {
            java.util.List<Object> list = new java.util.ArrayList<>();
            element.getAsJsonArray().forEach(item -> list.add(parseJsonElement(item)));
            return list;
        } else if (element.isJsonPrimitive()) {
            if (element.getAsJsonPrimitive().isString()) {
                return element.getAsString();
            } else if (element.getAsJsonPrimitive().isNumber()) {
                return element.getAsJsonPrimitive().getAsNumber();
            } else if (element.getAsJsonPrimitive().isBoolean()) {
                return element.getAsJsonPrimitive().getAsBoolean();
            }
        }
        return null;
    }
    
    /**
     * Convert object to pretty-printed JSON string
     */
    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
    
    /**
     * Convert object to compact JSON string
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return simpleGson.toJson(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
}

