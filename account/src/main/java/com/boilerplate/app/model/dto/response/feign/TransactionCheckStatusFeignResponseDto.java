package com.boilerplate.app.model.dto.response.feign;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionCheckStatusFeignResponseDto {
    private String id;
    private String code;
    private String invoiceNumber;
    private String acquirer;
    private String referenceNumber;
    private String merchantCode;
    private String tid;
    private String merchantName;
    private String nmid;
    private Long amount;
    private String traceNumber;
    private String status; // PENDING, PAID
    private String paidTime; // Format: "2025-12-03 10:24:44 +0700"
}

