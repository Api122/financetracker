package com.financetracker.domain;


import java.time.Instant;


public final class Transaction {

    private final Money amount;
    private final Instant date;
    private  final TransactionType type; // enum
    private  final String category;

    private Transaction(Money amount , Instant date , String category , TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;


    }

    public static Transaction of(Money oldamount , Instant olddate , String oldcategory , TransactionType oldtype){

        // null checks
        if(olddate == null){throw new NullPointerException("oldDate is null");}
        if(oldamount == null){throw new NullPointerException("oldamount is null");}
        if(oldcategory == null){throw new NullPointerException("oldcategory is null");}
        if(oldtype == null){throw new NullPointerException("oldtype is null");}
        if(oldcategory.isBlank()){throw new IllegalArgumentException("You have to add a category" + oldcategory);}
        if(oldamount.getAmountmoney().signum() < 0){throw new IllegalArgumentException("Amount cant be negative use transactionType enum to indicate expense or income");
        }


        return new Transaction(oldamount,olddate,oldcategory,oldtype);}


    public Money getAmount() {
        return amount;
    }

    public Instant getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return amount + " | " + type + " | " + category + " | " + date;
    }
}

