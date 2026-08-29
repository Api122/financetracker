package com.financetracker.domain.Exception;

public class IllegalAdditionException extends RuntimeException {
    public IllegalAdditionException(String message) {
        super(message);
    }
}
