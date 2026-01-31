package com.boilerplate.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResponseDto {
    private ResponseStatus responseStatus;
    private MerchantResponse merchantTransactionStatusResponse;
    private ConsumerData consumerData;
    private MerchantData merchantData;
    private TransactionData transactionData;

    @Getter
    @Setter
    public static class ResponseStatus {
        private String reason;
        private String responseCode;
    }

    @Getter
    @Setter
    public static class MerchantResponse {
        private String invoiceNumber;
    }

    @Getter
    @Setter
    public static class ConsumerData {
        @JsonProperty("cPan")
        private String cPan;
        private String issuerId;
    }

    @Getter
    @Setter
    public static class MerchantData {
        private String categoryCode;
        private String merchantId;
        @JsonProperty("mPan")
        private String mPan;
    }

    @Getter
    @Setter
    public static class TransactionData {
        private String additionalData;
        private String amountFee;
        private String approvalCode;
        private String captureDate;
        private String currency;
        private String localTransactionDate;
        private String localTransactionTime;
        private String pointOfServiceEntryMode;
        private String processingCode;
        private String rrn;
        private String settlementDate;
        private String stan;
        private BigDecimal totalAmount;
        private String transmissionDateTime;
        private CardAcceptorData cardAcceptorData;
        private SenderId senderId;

        @Getter
        @Setter
        public static class CardAcceptorData {
            private String cardAcceptorTerminalID;
        }

        @Getter
        @Setter
        public static class SenderId {
            private String acquiringInstitutionId;
            private String forwardingInstitutionId;
        }
    }
}

