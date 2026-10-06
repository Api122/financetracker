package com.financetracker.application;

public class IllegalAccountCreation extends RuntimeException {
    public IllegalAccountCreation(String message) {
        super(message);
    }
}
