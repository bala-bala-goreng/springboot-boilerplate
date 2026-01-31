package com.boilerplate.app.service;

import com.boilerplate.app.model.dto.response.BalanceSummaryResponse;
import com.boilerplate.app.model.dto.response.BankAccountResponse;
import com.boilerplate.app.model.entity.BankAccount;
import com.boilerplate.app.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final BankAccountRepository bankAccountRepository;

    @Transactional(readOnly = true)
    public List<BankAccountResponse> getAllAccounts() {
        log.info("Fetching all bank accounts");
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BalanceSummaryResponse getBalanceFromAllAccounts() {
        log.info("Fetching balance summary from all accounts");
        List<BankAccount> accounts = bankAccountRepository.findByStatus("ACTIVE");
        
        BigDecimal totalBalance = accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<BalanceSummaryResponse.AccountBalance> accountBalances = accounts.stream()
                .map(account -> BalanceSummaryResponse.AccountBalance.builder()
                        .accountNumber(account.getAccountNumber())
                        .accountName(account.getAccountName())
                        .bankCode(account.getBankCode())
                        .bankName(account.getBankName())
                        .balance(account.getBalance())
                        .currency(account.getCurrency())
                        .build())
                .collect(Collectors.toList());
        
        String currency = accounts.isEmpty() ? "IDR" : accounts.get(0).getCurrency();
        
        return BalanceSummaryResponse.builder()
                .totalAccounts(accounts.size())
                .totalBalance(totalBalance)
                .currency(currency)
                .accounts(accountBalances)
                .build();
    }

    private BankAccountResponse mapToResponse(BankAccount account) {
        return BankAccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .bankCode(account.getBankCode())
                .bankName(account.getBankName())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
