package com.boilerplate.app.constant;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    APPROVED("00", "Approved"),
    INVALID_MERCHANT("03", "Invalid Merchant"),
    DO_NOT_HONOR("05", "Do Not Honor"),
    INVALID_TRANSACTION("12", "Invalid Transaction"),
    INVALID_AMOUNT("13", "Invalid Amount"),
    INVALID_PAN_NUMBER("14", "Invalid PAN Number"),
    FORMAT_ERROR("30", "Format Error"),
    INSUFFICIENT_FUNDS("51", "Insufficient Funds"),
    TRANSACTION_NOT_PERMITTED_CARDHOLDER("57", "Transaction Not Permitted to Cardholder / QR is Expired"),
    TRANSACTION_NOT_PERMITTED_TERMINAL("58", "Transaction Not Permitted to Terminal"),
    SUSPECTED_FRAUD("59", "Suspected Fraud"),
    EXCEEDS_TRANSACTION_AMOUNT_LIMIT("61", "Exceeds Transaction Amount Limit"),
    RESTRICTED_CARD("62", "Restricted Card"),
    EXCEEDS_TRANSACTION_FREQUENCY_LIMIT("65", "Exceeds Transaction Frequency Limit"),
    SUSPEND_TRANSACTION("68", "Suspend Transaction"),
    CUT_OFF_IN_PROGRESS("90", "Cut-Off In Progress"),
    LINK_DOWN("91", "Link Down"),
    INVALID_ROUTING("92", "Invalid Routing"),
    DUPLICATE_TRANSMISSION("93", "Duplicate Transmission / Duplicate QR"),
    SYSTEM_MALFUNCTION("96", "System Malfunction"),;

    private final String code;
    private final String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

