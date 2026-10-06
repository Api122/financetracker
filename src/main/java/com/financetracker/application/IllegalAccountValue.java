package com.financetracker.application;

public class IllegalAccountValue extends RuntimeException {
    public IllegalAccountValue(String message) {
        super(message);
    }
}
