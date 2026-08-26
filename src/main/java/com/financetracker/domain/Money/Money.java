package com.financetracker.domain.Money;

import com.financetracker.domain.Exception.IllegalCurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Set;

public class Money {
     // final and private for security and immutability
     private final BigDecimal amount;   // no errors with decimal points
     private final Currency currency;



    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    private static final Set<Currency> VALID_CURRENCY =
            Set.of(
                    Currency.getInstance("USD"),
                    Currency.getInstance("GDP"),
                    Currency.getInstance("EUR")


            );

    private static final int roundingval = 2;
    private static final RoundingMode rounder = RoundingMode.HALF_EVEN;

    public static BigDecimal normalize(BigDecimal amount){
        return amount.setScale(2,rounder);
    }




    public static Money of(BigDecimal rAmount , Currency currency){

        //null checks

        if(rAmount == null){throw new NullPointerException("Null value of amount ");}
        if(currency == null){throw new NullPointerException("NULL VALUE of currency");}
        // checks in relation to money

        if(!VALID_CURRENCY.contains(currency)) {throw new IllegalCurrencyException("Invalid value given is " + currency);}
        /*
         TODO LATER:  when building the API layer, validate/catch invalid ISO codes
         from external input before calling Currency.getInstance(...)
        */


        return new Money(normalize(rAmount),currency);
    }

    public BigDecimal getAmount(BigDecimal inputAmount){
        return amount;
    }

    public Currency getCurrency(Currency inputCurrency){
        return currency;
    }


    public static BigDecimal amountAddition(BigDecimal a1, BigDecimal a2){
        return a1.add(a2);
    }

    public static Money moneyAddition(Money money1,Money money2){
        if(money1.currency != money2.currency){throw new IllegalCurrencyException("Currencies dont match, input 1 is" + money1.currency + ", input 2 is" + money2.currency);}

        return new Money(amountAddition(money1.amount,money2.amount),money1.currency);




    }

    public @Override boolean equals(Object e){
        if(this.e == )
    }





}


