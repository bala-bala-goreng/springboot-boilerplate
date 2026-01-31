package com.boilerplate.app.model.dto.request.feign;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionCheckStatusFeignRequestDto {

    private String switching;
    private String processingCode;
    private String approvalCode;
    private String currencyNumber;
    private String retrievalReferenceNumber;
    private BigDecimal feeAmount;
    private BigDecimal transactionAmount;
    private String systemTraceAuditNumber;
    private String transmissionDateTime;
    private String localTransactionDateTime;
    private String settlementDate;
    private String captureDate;
    private String forwardingId;
    private String posEntryMode;
    private String additionalData;
    private String additionalDataNational;

    private String merchantPan;
    private String merchantCategoryCode;
    private String acquirerId;
    private String terminalId;
    private String merchantId;
    private String merchantName;

    private String customerPan;
    private String issuerId;
    private String customerData;
    private String qrReferenceNumber;

    private String payload;
}

