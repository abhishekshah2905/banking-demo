package com.poc.banking.transactions.service;

import com.poc.banking.accounts.entity.AccountEntity;
import com.poc.banking.accounts.repository.AccountRepository;
import com.poc.banking.transactions.entity.TransactionEntity;
import com.poc.banking.transactions.model.Transaction;
import com.poc.banking.transactions.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public List<Transaction> getTransactions(UUID accountId, UUID userId) {

        accountRepository.findByIdAndUsersId(accountId, userId).orElseThrow(() -> new RuntimeException("Account not exist"));

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(entity -> new Transaction(accountId, entity.getType()
                        , entity.getAmount(), entity.getReference()))
                .collect(Collectors.toList());
    }

    public void createTransaction(Transaction transaction) {
        AccountEntity account = accountRepository.findById(transaction.getAccountId()).orElseThrow(() -> new RuntimeException("Account not exist"));
        TransactionEntity entity = new TransactionEntity();
        entity.setReference(transaction.getReference());
        entity.setType(transaction.getType());
        entity.setAmount(transaction.getAmount());
        entity.setAccount(account);
        transactionRepository.save(entity);
    }

}
