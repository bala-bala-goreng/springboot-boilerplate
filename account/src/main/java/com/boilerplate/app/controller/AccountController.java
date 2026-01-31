package com.boilerplate.app.controller;

import com.boilerplate.app.model.dto.response.BalanceSummaryResponse;
import com.boilerplate.app.model.dto.response.BankAccountResponse;
import com.boilerplate.app.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Bank account management APIs")
public class AccountController {

    private final AccountService accountService;

    @Operation(
        summary = "Get list of all bank accounts",
        description = "Retrieves a list of all bank accounts in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of accounts"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountResponse>> getAllAccounts() {
        log.info("Received request to get all bank accounts");
        List<BankAccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @Operation(
        summary = "Get balance from all accounts",
        description = "Retrieves balance summary from all active bank accounts including total balance and individual account balances"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved balance summary"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceSummaryResponse> getBalanceFromAllAccounts() {
        log.info("Received request to get balance from all accounts");
        BalanceSummaryResponse balanceSummary = accountService.getBalanceFromAllAccounts();
        return ResponseEntity.ok(balanceSummary);
    }
}
