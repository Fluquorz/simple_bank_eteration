package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Insufficient balance for the transaction.");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientBalanceException(Throwable cause) {
        super(cause);
    }
}
