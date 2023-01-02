package com.poc.banking.utils;

import com.poc.banking.accounts.model.Account;
import com.poc.banking.accounts.model.AccountType;
import com.poc.banking.admin.model.CreateUserCommand;
import com.poc.banking.admin.model.FindUserCommand;
import com.poc.banking.security.model.LoginRequest;
import com.poc.banking.users.entity.Authorities;
import com.poc.banking.users.model.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static com.poc.banking.util.AppConstants.ROLE_ACCOUNT;
import static com.poc.banking.util.AppConstants.ROLE_USER;

public class TestUtils {

    public static CreateUserCommand buildCreateUserCommand() {
        CreateUserCommand createUserCommand = new CreateUserCommand();
        createUserCommand.setUserName("username");
        createUserCommand.setPassword("password");
        createUserCommand.setFirstName("First name");
        createUserCommand.setLastName("Last name");
        createUserCommand.setAuthorities(Arrays.asList(ROLE_USER, ROLE_ACCOUNT));
        return createUserCommand;
    }

    public static Authorities buildAuthoritiesEntity(String authority) {
        Authorities authorities = new Authorities();
        authorities.setAuthority(authority);
        authorities.setId(UUID.randomUUID());
        return authorities;
    }

    public static LoginRequest adminLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");
        return loginRequest;
    }

    public static Account buildAccountRequest(User user) {
        Account account = new Account();
        account.setAccountNumber(RandomStringUtils.randomNumeric(1000));
        account.setType(AccountType.CURRENT);
        account.setBalance(BigDecimal.valueOf(100));
        account.setUserId(user.getId());
        return account;
    }

    public static CreateUserCommand buildUserCommand() {
        CreateUserCommand userCommand = new CreateUserCommand();
        userCommand.setUserName("username");
        userCommand.setPassword("password");
        userCommand.setFirstName("FirstName");
        userCommand.setLastName("LastName");
        userCommand.setAuthorities(Arrays.asList(ROLE_USER, ROLE_ACCOUNT));
        return userCommand;
    }

    public static FindUserCommand findUserCommand(String searchStr) {
        FindUserCommand findUserCommand = new FindUserCommand();
        findUserCommand.setQuery(searchStr);
        return findUserCommand;
    }

}