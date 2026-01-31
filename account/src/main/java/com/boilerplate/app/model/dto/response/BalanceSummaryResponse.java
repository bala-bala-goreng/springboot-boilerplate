package com.boilerplate.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceSummaryResponse {
    private Integer totalAccounts;
    private BigDecimal totalBalance;
    private String currency;
    private List<AccountBalance> accounts;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountBalance {
        private String accountNumber;
        private String accountName;
        private String bankCode;
        private String bankName;
        private BigDecimal balance;
        private String currency;
    }
}
