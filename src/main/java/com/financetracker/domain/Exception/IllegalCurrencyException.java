package com.financetracker.domain.Exception;

public class IllegalCurrencyException extends DomainException {
    public IllegalCurrencyException(String c) {
        super(c);
    }
}
