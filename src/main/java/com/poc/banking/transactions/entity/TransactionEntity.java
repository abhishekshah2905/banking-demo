package com.poc.banking.transactions.entity;

import com.poc.banking.accounts.entity.AccountEntity;
import com.poc.banking.audit.AbstractAuditingEntity;
import com.poc.banking.transactions.model.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "account_transaction")
public class TransactionEntity extends AbstractAuditingEntity {

    private String reference;

    private TransactionType type;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

}
