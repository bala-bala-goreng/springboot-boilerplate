package com.boilerplate.app.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QrisRefundRequestDto {
    
    @NotBlank
    private String id;
    
    @NotBlank
    private String transactionId;
    
    @NotBlank
    private String invoiceNumber;
    
    @NotBlank
    private String retrievalReferenceNumber;
    
    @NotBlank
    private String systemTraceAuditNumber;
    
    @NotNull
    private BigDecimal amount;
    
    @NotBlank
    private String refundTime; // YYYYMMDDHHMMSS
    
    @NotBlank
    private String payload; // Original json payload from switching as string
}

