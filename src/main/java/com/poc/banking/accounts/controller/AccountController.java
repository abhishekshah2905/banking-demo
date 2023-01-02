package com.poc.banking.accounts.controller;

import com.poc.banking.accounts.model.Account;
import com.poc.banking.accounts.model.AccountBalance;
import com.poc.banking.accounts.model.MoneyTransfer;
import com.poc.banking.accounts.service.AccountService;
import com.poc.banking.security.SecurityUtil;
import com.poc.banking.transactions.model.Transaction;
import com.poc.banking.transactions.service.TransactionService;
import com.poc.banking.users.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    @GetMapping("/accounts")
    @Operation(summary = "Retrieve a accounts.")
    @ApiResponse(responseCode = "200", description = "Accounts found.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))})
    public List<Account> getAccounts() {
        return accountService.getAccounts(SecurityUtil.getUserId());
    }

    @GetMapping("/accounts/{id}")
    @Operation(summary = "Retrieve a account with provided identifier")
    @ApiResponse(responseCode = "200", description = "Account found.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public Account getAccount(@PathVariable UUID id) {
        return accountService.getAccount(id, SecurityUtil.getUserId());
    }

    @GetMapping("/accounts/{id}/balance")
    @Operation(summary = "Retrieve a account balance with provided identifier")
    @ApiResponse(responseCode = "200", description = "Account found.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountBalance.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public AccountBalance getAccountBalance(@PathVariable UUID id) {
        return accountService.getBalance(id, SecurityUtil.getUserId());
    }


    @PostMapping("/accounts/{fromAccountId}/transfer/{toAccountId}")
    @Operation(summary = "Transfer money.")
    @ApiResponse(responseCode = "200", description = "Money transferred successfully..",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Account.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public void transfer(@PathVariable UUID fromAccountId, @PathVariable UUID toAccountId, @Valid @NotNull @RequestBody MoneyTransfer transfer) {
        accountService.transferAmount(fromAccountId, toAccountId, transfer.getAmount(), SecurityUtil.getUserId());
    }

    @GetMapping("/accounts/{id}/transactions")
    @Operation(summary = "Retrieve a transaction with provided identifier")
    @ApiResponse(responseCode = "200", description = "Transactions found.",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Transaction.class))})
    @ApiResponse(responseCode = "404", description = "Account not found.", content = @Content)
    public List<Transaction> getTransactions(@PathVariable UUID id) {
        return transactionService.getTransactions(id, SecurityUtil.getUserId());
    }


}
