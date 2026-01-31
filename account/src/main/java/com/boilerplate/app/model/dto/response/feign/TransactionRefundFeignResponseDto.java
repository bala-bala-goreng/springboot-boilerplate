package com.boilerplate.app.model.dto.response.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Artajasa QR Refund Response DTO matching QRRefundRS specification
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRefundFeignResponseDto {
    
    @JsonProperty("QRRefundRS")
    private QRRefundRS qrRefundRS;

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QRRefundRS {
        // 4 bytes - fix Value must be "0210" for response message
        private String msgType;
        // 19 bytes - max Must contain the same value from the original QR Refund Request
        private String trxPAN;
        // 6 bytes - fix Must contain the same value from the original QR Refund Request
        private String trxCode;
        // 12 bytes - fix Must contain the same value from the original QR Refund Request
        private String trxAmount;
        // 12 bytes - fix Must contain the same value from the original QR Refund Request
        private String settlementAmount;
        // 12 bytes - fix Must contain the same value from the original QR Refund Request
        private String accHolderAmount;
        // 10 bytes - fix Date and time message initiated in GMT. Format: "MMDDhhmmss"
        private String transmissionDateTime;
        // 8 bytes - fix Must contain the same value from the original QR Refund Request
        private String settlementConversionRate;
        // 8 bytes - fix Must contain the same value from the original QR Refund Request
        private String accHolderConversionRate;
        // 6 bytes - fix Must contain the same value from the original QR Refund Request
        private String msgSTAN;
        // 6 bytes - fix Must contain the same value from the original QR Refund Request
        private String trxTime;
        // 4 bytes - fix Must contain the same value from the original QR Refund Request
        private String trxDate;
        // 4 bytes - fix From Issuer: same as request. To Acquirer: filled by QR Artajasa Network
        private String settlementDate;
        // 4 bytes - fix Must contain the same value from the original QR Refund Request
        private String conversionDate;
        // 4 bytes - fix Must contain the same value from the original QR Refund Request
        private String captureDate;
        // 4 bytes - fix Must contain the same value from the original QR Refund Request
        private String merchantType;
        // 3 bytes - fix Must contain the same value from the original QR Refund Request
        private String posEntryMode;
        // 9 bytes - fix Must contain the same value from the original QR Refund Request
        private String trxFeeAmount;
        // 11 bytes - max Must contain the same value from the original QR Refund Request
        private String acquirerID;
        // 11 bytes - max Must contain the same value from the original QR Refund Request
        private String forwardingID;
        // 12 bytes - fix Must contain the same value from the original QR Refund Request
        private String retrievalReferenceNumber;
        // 6 bytes - fix Authorization code from issuer (six digit unique code)
        private String approvalCode;
        // 2 bytes - fix Response code from Response Code Table
        private String responseCode;
        // 16 bytes - fix Must contain the same value from the original QR Refund Request
        private String terminalID;
        // 15 bytes - fix Must contain the same value from the original QR Refund Request
        private String merchantID;
        // 40 bytes - fix Must contain the same value from the original QR Refund Request
        private String merchantNameLocation;
        // 255 bytes - max Must contain the same value from the original QR Refund Request
        private String additionalDataPrivate;
        // 3 bytes - fix Must contain the same value from the original QR Refund Request
        private String trxCurrencyCode;
        // 3 bytes - fix Must contain the same value from the original QR Refund Request
        private String settlementCurrencyCode;
        // 3 bytes - fix Must contain the same value from the original QR Refund Request
        private String accHolderCurrencyCode;
        // 11 bytes - max Must contain the same value from the original QR Refund Request
        private String issuerID;
        // 19 bytes - max Must contain the same value from the original QR Refund Request
        private String fromAccount;
        // 20 bytes - fix Must contain the same value from the original QR Refund Request
        private String invoiceNumber;
    }
}

