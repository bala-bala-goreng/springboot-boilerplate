package com.boilerplate.app.util;

import com.boilerplate.app.model.dto.response.QRCheckStatusResponseDto;
import com.boilerplate.app.model.dto.response.QRPaymentCreditResponseDto;
import com.boilerplate.app.model.dto.response.QrisRefundResponseDto;

public class ResponseCodeExtractor {

    private ResponseCodeExtractor() {}

    public static String extractResponseCode(QRPaymentCreditResponseDto response) {
        return response != null && response.getQrPaymentCreditRS() != null
            ? response.getQrPaymentCreditRS().getResponseCode()
            : null;
    }

    public static String extractResponseCode(QRCheckStatusResponseDto response) {
        return response != null && response.getQrCheckStatusRS() != null
            ? response.getQrCheckStatusRS().getResponseCode()
            : null;
    }

    public static String extractResponseCode(QrisRefundResponseDto response) {
        return response != null && response.getData() != null
            ? response.getData().getResponseCode()
            : null;
    }
}
