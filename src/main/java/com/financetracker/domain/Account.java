package com.financetracker.domain;

import java.math.BigDecimal;
import java.util.*;

public class Account {

    private final String accountID;
    private final Currency currency;
    private final List<Transaction> transactions;
    private final List<Transaction> transactionHistory;

    private Account(String accountID, Currency currency){

        this.accountID = accountID;
        this.currency = currency;
        this.transactions = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();


        // null checks
        if(currency == null){throw new NullPointerException("Currency is null");}
        if(accountID == null){throw  new NullPointerException("accountID is null");}
        if(accountID.isBlank()){throw new IllegalArgumentException("accountID cannot be blank");}
    }

    public static Account of(String accountID , Currency currency){
        return new Account(accountID,currency);
    }
    public String getAccountID() {
        return accountID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
    public List<Transaction> getTransactionHistory(){
        return Collections.unmodifiableList(transactionHistory);
    }

    public void addNewTransaction(Transaction t1){
        if(t1== null){throw new NullPointerException("transaction input is null");}
        if(!t1.getAmount().getCurrency().equals(currency)){throw new IllegalArgumentException("transaction currency doesnt match account currency");
        }
        transactions.add(t1);
        transactionHistory.add(t1);
    }

    public void removeTransaction(Transaction t1){
        if(t1 == null){throw new NullPointerException("Transaction input is null");}
        if(!t1.getAmount().getCurrency().equals(currency)){throw new IllegalArgumentException("transaction currency does not match account currency ");}
        transactions.remove(t1);
    }

    public Money getBalance(){
        Money balance = Money.of(BigDecimal.ZERO,currency);
        for(Transaction t : transactions){
            if(t.getType() == TransactionType.INCOME){
                balance = Money.add(balance,t.getAmount());
            }
            else{
                balance = Money.add(balance, Money.of(t.getAmount().getAmountmoney().negate(),currency));
            }
        }
        return balance;
    }

    @Override
    public boolean equals(Object e){
        if(this == e){return true;}
        if(e == null || e.getClass() != getClass()){return false;}

        Account other = (Account) e;
        return accountID.equals(other.accountID) && currency.equals(other.currency) && transactions.equals(other.transactions);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(accountID,currency,transactions);
    }

    @Override
    public String toString() {
        return "AccountID is:" +accountID+ "Account Currency is:" + currency + "Transactions are:" + transactions;
    }
}

