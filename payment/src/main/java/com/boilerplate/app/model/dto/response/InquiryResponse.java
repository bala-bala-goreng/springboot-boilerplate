package com.boilerplate.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse {
    private String billerCode;
    private String billerName;
    private String customerNumber;
    private String customerName;
    private BigDecimal amount;
    private String currency;
    private String period;
    private String additionalInfo;
}
