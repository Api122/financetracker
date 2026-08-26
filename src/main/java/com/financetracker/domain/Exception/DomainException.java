package com.financetracker.domain.Exception;

public abstract class DomainException extends RuntimeException{

    public DomainException(String msg){
        super(msg);
    }
}
