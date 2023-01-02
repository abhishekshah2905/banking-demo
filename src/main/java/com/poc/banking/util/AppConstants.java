package com.poc.banking.util;

public interface AppConstants {

    String ROLE_ACCOUNT = "ROLE_ACCOUNT";
    String ROLE_USER = "ROLE_USER";
    String ROLE_ADMIN = "ROLE_ADMIN";
    String CANNOT_WITHDRAW_MONEY_FROM_AN_EMPTY_ACCOUNT = "Cannot withdraw money from an empty account";
    String CANNOT_WITHDRAW_MORE_MONEY_THAN_IS_CURRENTLY_IN_THE_ACCOUNT =
            "Cannot withdraw more money than is currently in the account";
    String CANNOT_DEPOSIT_OR_WITHDRAW_NEGATIVE_AMOUNT = "Cannot deposit or withdraw negative amount";
    String CANNOT_DEPOSIT_OR_WITHDRAW_ZERO_AMOUNT = "Cannot deposit or withdraw zero amount";
    String USER_NOT_FOUND = "User not found";
    String ACCOUNT_NOT_FOUND = "Account not found";
}