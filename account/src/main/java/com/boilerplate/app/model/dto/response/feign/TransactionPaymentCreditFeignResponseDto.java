package com.boilerplate.app.model.dto.response.feign;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionPaymentCreditFeignResponseDto {
    private String id;
    private String code;
    private String invoiceNumber;
}

