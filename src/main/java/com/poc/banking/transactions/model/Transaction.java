package com.poc.banking.transactions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private UUID accountId;

    private TransactionType type;

    private BigDecimal amount;

    private String reference;

}
