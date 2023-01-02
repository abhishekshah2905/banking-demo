package com.poc.banking.accounts.model;

import com.poc.banking.transactions.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private UUID id;

    private UUID userId;

    private BigDecimal balance;

    private AccountType type;

    private String accountNumber;

    private List<Transaction> transactions;

}
