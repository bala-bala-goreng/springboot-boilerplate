package com.boilerplate.app.util;

import org.springframework.http.HttpStatus;

/**
 * Utility to map Artajasa response codes to HTTP status codes following RFC standards
 */
public class HttpStatusMapper {

    /**
     * Map Artajasa response code to HTTP status code
     * 
     * @param responseCode Artajasa response code (e.g., "00", "03", "30", etc.)
     * @return HTTP status code following RFC standards
     */
    public static HttpStatus mapToHttpStatus(String responseCode) {
        if (responseCode == null || responseCode.isEmpty()) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Success
        if ("00".equals(responseCode)) {
            return HttpStatus.OK;
        }

        // Client errors (4xx) - Invalid request, invalid merchant, format errors, etc.
        if (isClientError(responseCode)) {
            return HttpStatus.BAD_REQUEST; // 400
        }

        // Service unavailable (503) - Cutoff, link down, duplicate transmission, etc.
        if (isServiceUnavailable(responseCode)) {
            return HttpStatus.SERVICE_UNAVAILABLE; // 503
        }

        // Server errors (5xx) - System malfunction, etc.
        return HttpStatus.INTERNAL_SERVER_ERROR; // 500
    }

    /**
     * Check if response code indicates a client error (4xx)
     */
    private static boolean isClientError(String responseCode) {
        return "03".equals(responseCode) || // Invalid Merchant
               "05".equals(responseCode) || // Do Not Honor
               "12".equals(responseCode) || // Invalid Transaction
               "13".equals(responseCode) || // Invalid Amount
               "14".equals(responseCode) || // Invalid PAN Number
               "30".equals(responseCode) || // Format Error
               "51".equals(responseCode) || // Insufficient Funds
               "57".equals(responseCode) || // Transaction Not Permitted to Cardholder / QR is Expired
               "58".equals(responseCode) || // Transaction Not Permitted to Terminal
               "59".equals(responseCode) || // Suspected Fraud
               "61".equals(responseCode) || // Exceeds Transaction Amount Limit
               "62".equals(responseCode) || // Restricted Card
               "65".equals(responseCode);   // Exceeds Transaction Frequency Limit
    }

    /**
     * Check if response code indicates service unavailable (503)
     */
    private static boolean isServiceUnavailable(String responseCode) {
        return "68".equals(responseCode) || // Suspend Transaction
               "90".equals(responseCode) || // Cut-Off In Progress
               "91".equals(responseCode) || // Link Down
               "92".equals(responseCode) || // Invalid Routing
               "93".equals(responseCode);   // Duplicate Transmission / Duplicate QR
    }
}

