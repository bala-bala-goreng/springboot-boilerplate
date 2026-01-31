package com.boilerplate.app.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.boilerplate.app.config.annotation.DatePattern;
import com.boilerplate.app.constant.DateFormatEnum;
import jakarta.validation.Valid;
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
public class PaymentRequestDto {
    @NotNull
    @Valid
    private ConsumerData consumerData;
    @NotNull
    @Valid
    private MerchantData merchantData;
    @NotNull
    @Valid
    private TransactionData transactionData;

    @Getter
    @Setter
    public static class ConsumerData {
        @NotBlank
        @JsonProperty("cPan")
        private String cPan;
        @NotBlank
        private String issuerId;
    }

    @Getter
    @Setter
    public static class MerchantData {
        @NotBlank
        private String categoryCode;
        @NotBlank
        private String merchantId;
        @NotNull
        @JsonProperty("mPan")
        private String mPan;
    }

    @Getter
    @Setter
    public static class TransactionData {
        @NotBlank
        private String additionalData;
        @NotBlank
        private String additionalDataNational;
        private String amountFee;
        @NotBlank
        private String approvalCode;
        @NotBlank
        @DatePattern(format = DateFormatEnum.MMDD)
        private String captureDate;
        @NotBlank
        private String currency;
        @NotBlank
        @DatePattern(format = DateFormatEnum.MMDD)
        private String localTransactionDate;
        @NotBlank
        @DatePattern(format = DateFormatEnum.HHMMSS)
        private String localTransactionTime;
        @NotBlank
        private String pointOfServiceEntryMode;
        @NotBlank
        private String processingCode;
        @NotBlank
        private String rrn;
        @DatePattern(format = DateFormatEnum.MMDD)
        private String settlementDate;
        @NotBlank
        private String stan;
        @NotNull
        private BigDecimal totalAmount;
        @NotBlank
        @DatePattern(format = DateFormatEnum.MMDDHHMMSS)
        private String transmissionDateTime;
        @NotNull
        @Valid
        private CardAcceptorData cardAcceptorData;
        @NotNull
        @Valid
        private SenderId senderId;

        @Getter
        @Setter
        public static class CardAcceptorData {
            @NotBlank
            private String cardAcceptorCity;
            @NotBlank
            private String cardAcceptorCountryCode;
            @NotBlank
            private String cardAcceptorName;
            @NotBlank
            private String cardAcceptorTerminalID;
        }

        @Getter
        @Setter
        public static class SenderId {
            @NotBlank
            private String acquiringInstitutionId;
            private String forwardingInstitutionId;
        }
    }
}

