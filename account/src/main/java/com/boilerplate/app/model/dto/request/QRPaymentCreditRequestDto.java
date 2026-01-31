package com.boilerplate.app.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QRPaymentCreditRequestDto {
    
    @NotNull
    @Valid
    @JsonProperty("QRPaymentCreditRQ")
    private QRPaymentCreditRQ qrPaymentCreditRQ;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QRPaymentCreditRQ {
        private String msgType;
        private String trxPAN;
        private String trxCode;
        private String trxAmount;
        private String settlementAmount;
        private String accHolderAmount;
        private String transmissionDateTime;
        private String settlementConversionRate;
        private String accHolderConversionRate;
        private String msgSTAN;
        private String trxTime;
        private String trxDate;
        private String settlementDate;
        private String conversionDate;
        private String captureDate;
        private String merchantType;
        private String posEntryMode;
        private String trxFeeAmount;
        private String acquirerID;
        private String forwardingID;
        private String retrievalReferenceNumber;
        private String approvalCode;
        private String terminalID;
        private String merchantID;
        private String merchantNameLocation;
        private String additionalDataPrivate;
        private String trxCurrencyCode;
        private String settlementCurrencyCode;
        private String accHolderCurrencyCode;
        private String additionalDataNational;
        private String issuerID;
        private String fromAccount;
    }
}

