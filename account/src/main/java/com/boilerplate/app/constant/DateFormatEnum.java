package com.boilerplate.app.constant;

import lombok.Getter;

@Getter
public enum DateFormatEnum {
    MMDD("MMdd"),
    HHMMSS("HHmmss"),
    YYYY_MM_DD_T_HH_MM_SS_SSSTZD("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
    MMDDHHMMSS("MMddHHmmss"),
    YYYYMMDDHHMMSS("yyyyMMddHHmmss");

    private String format;

    DateFormatEnum(String format) {
        this.format = format;
    }
}

