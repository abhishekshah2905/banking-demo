package com.poc.banking.exceptions;

public class BankAccountActionInvalidException extends RuntimeException {

    public BankAccountActionInvalidException() {
    }

    public BankAccountActionInvalidException(String message) {
        super(message);
    }

    public BankAccountActionInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankAccountActionInvalidException(Throwable cause) {
        super(cause);
    }
}
