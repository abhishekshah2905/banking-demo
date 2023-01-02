package com.poc.banking.accounts.service;

import com.poc.banking.accounts.entity.AccountEntity;
import com.poc.banking.accounts.model.Account;
import com.poc.banking.accounts.model.AccountBalance;
import com.poc.banking.accounts.repository.AccountRepository;
import com.poc.banking.exceptions.BankAccountActionInvalidException;
import com.poc.banking.exceptions.NotFoundException;
import com.poc.banking.transactions.model.Transaction;
import com.poc.banking.transactions.model.TransactionType;
import com.poc.banking.transactions.service.TransactionService;
import com.poc.banking.users.entity.UserEntity;
import com.poc.banking.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import static com.poc.banking.util.AppConstants.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public AccountBalance getBalance(UUID accountId, UUID userId) throws BankAccountActionInvalidException {
        log.trace("Get Balance [accountId: {}]", accountId);
        AccountEntity account = this.accountRepository.findByIdAndUsersId(accountId, userId).
                orElseThrow(() -> new NotFoundException(String.format("Account not found: %s", accountId)));
        return new AccountBalance(account.getBalance());
    }

    @Transactional(readOnly = true)
    public List<Account> getAccounts(UUID userId) {
        log.trace("Get Accounts [userId: {}]", userId);
        var accounts = accountRepository.findByUsersId(userId).stream().map(entity -> toModel(entity, userId))
                .collect(Collectors.toList());
        log.trace("Get Accounts [userId: {}, total account: {}]", userId, accounts.size());
        return accounts;
    }

    @Transactional(readOnly = true)
    public Account getAccount(UUID accountId, UUID userId) {
        log.trace("Get Account [accountId: {}]", accountId);
        var account = accountRepository.findByIdAndUsersId(accountId, userId).map(entity -> toModel(entity, userId))
                .orElseThrow(() -> new NotFoundException("Account not found"));
        log.trace("Get Account [accountId: {}]", accountId);
        return account;
    }

    @Transactional
    public Account createAccount(Account account) {
        log.trace("Create account [account {}]", account);

        AccountEntity entity = toEntity(account);
        UserEntity user = usersRepository.findById(account.getUserId()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        entity.addUserToAccount(user);
        accountRepository.save(entity);

        if (entity.getBalance().doubleValue() > 0.0) {
            final Transaction transaction = new Transaction(entity.getId(), TransactionType.CREDIT, entity.getBalance(), "Account opening");
            transactionService.createTransaction(transaction);
            log.debug("New account transaction is created for account {}", entity.getId());
        }

        log.trace("Account created [account {}]", account);
        return this.toModel(entity, user.getId());
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transferAmount(UUID fromAccountId, UUID toAccountId, BigDecimal amount, UUID userId) throws BankAccountActionInvalidException {
        log.debug("Transfer amount [fromAccountId: {}, toAccountId: {}, amount: {}]", fromAccountId, toAccountId, amount);
        validateAmount(amount);

        AccountEntity fromAccount = this.accountRepository.findByIdAndUsersId(fromAccountId, userId).
                orElseThrow(() -> new NotFoundException(String.format("From Account not found: %s", fromAccountId)));
        validate(() -> fromAccount.getBalance().doubleValue() == 0, CANNOT_WITHDRAW_MONEY_FROM_AN_EMPTY_ACCOUNT);
        validate(() -> amount.doubleValue() > fromAccount.getBalance().doubleValue(), CANNOT_WITHDRAW_MORE_MONEY_THAN_IS_CURRENTLY_IN_THE_ACCOUNT);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);


        AccountEntity toAccount = this.accountRepository.findById(toAccountId).
                orElseThrow(() -> new NotFoundException(String.format("To Account not found: %s", fromAccountId)));
        validateAmount(amount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);

        transactionService.createTransaction(
                new Transaction(fromAccountId, TransactionType.DEBIT, amount, toAccount.getId().toString()));
        transactionService.createTransaction(
                new Transaction(toAccountId, TransactionType.CREDIT, amount, fromAccount.getId().toString()));

        log.debug("Transferred amount [fromAccountId: {}, toAccountId: {}, amount: {}]", fromAccountId, toAccountId, amount);
    }

    @Transactional
    public void addUserIntoAccount(UUID accountId, UUID userId) {
        log.debug("Add User into Account [accountId: {}, userId: {}]", accountId, userId);
        AccountEntity account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format("Account not found: %s", accountId)));

        UserEntity user = this.usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User not found: %s", userId)));

        account.addUserToAccount(user);
        accountRepository.save(account);
        log.debug("User added into Account [accountId: {}, userId: {}]", accountId, userId);
    }

    @Transactional
    public void removeUserFromAccount(UUID accountId, UUID userId) {
        log.debug("Remove User into Account [accountId: {}, userId: {}]", accountId, userId);
        AccountEntity account = this.accountRepository.findByIdAndUsersId(accountId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Account not found: %s", accountId)));

        account.removeUserFromAccount(userId);
        accountRepository.save(account);
        log.debug("User removed from Account [accountId: {}, userId: {}]", accountId, userId);
    }


    private void validateAmount(BigDecimal amount) throws BankAccountActionInvalidException {
        validate(() -> amount.doubleValue() < 0, CANNOT_DEPOSIT_OR_WITHDRAW_NEGATIVE_AMOUNT);
        validate(() -> amount.doubleValue() == 0, CANNOT_DEPOSIT_OR_WITHDRAW_ZERO_AMOUNT);
    }


    private void validate(BooleanSupplier condition, String message) throws BankAccountActionInvalidException {
        if (condition.getAsBoolean()) {
            throw new BankAccountActionInvalidException(message);
        }
    }

    private Account toModel(AccountEntity entity, UUID userId) {
        return new Account(entity.getId(), userId, entity.getBalance(), entity.getType(), entity.getAccountNumber(),
                transactionService.getTransactions(entity.getId(), userId));
    }

    private AccountEntity toEntity(Account account) {

        AccountEntity entity = new AccountEntity();
        entity.setAccountNumber(account.getAccountNumber());
        entity.setBalance(account.getBalance());
        entity.setType(account.getType());

        return entity;
    }
}
