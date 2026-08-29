package com.financetracker.domaintest;
import com.financetracker.domain.Exception.IllegalAdditionException;
import com.financetracker.domain.Money;
import com.financetracker.domain.Exception.IllegalCurrencyException;


import  org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static com.financetracker.domain.Money.add;
import static com.financetracker.domain.Money.negate;
import static org.junit.jupiter.api.Assertions.*;

// tests to do check if same money are equals check if addition of money works , check if the error of adding different currienies works ,
// and check if money is automatically normalized

public class MoneyTest {

    @Test  void constructingValidAmountandCurrency() {
        Money money = Money.of(new BigDecimal("20"), Currency.getInstance("USD"));

        assertEquals(new BigDecimal("20.00"), money.getAmount());
        assertEquals(Currency.getInstance("USD"), money.getCurrency());
    }

    @Test void constructingInvalidCurrencyThrowsException(){
        assertThrows(IllegalCurrencyException.class,() ->
        Money.of(new BigDecimal("20"),Currency.getInstance("JPY")));
    }

    @Test void constructingWithNullAmountThrowsNullPointerException(){
        assertThrows(NullPointerException.class,()->
        Money.of(null,Currency.getInstance("USD")));
    }

    @Test void constructingWithNullCurrencyThrowsNullPointerException(){
        assertThrows(NullPointerException.class,()->
        Money.of(new BigDecimal("10"),null));
    }

    @Test void sameAmountsWithDifferentDecimalsAreEqual(){
        Money money1 = Money.of(new BigDecimal("10"),Currency.getInstance("EUR"));
        Money money2 = Money.of(new BigDecimal("10.00"),Currency.getInstance("EUR"));

        assertEquals(money1,money2);
    }
   @Test void sameAmountWithDifferentCurrenciesAreNotEqual(){
       Money money1 = Money.of(new BigDecimal("10"),Currency.getInstance("EUR"));
       Money money2 = Money.of(new BigDecimal("10.00"),Currency.getInstance("GBP"));

       assertNotEquals(money1,money2);

   }
   @Test void sameCurrencyWithDifferentAmountAreNotEqual(){
       Money money1 = Money.of(new BigDecimal("27"),Currency.getInstance("EUR"));
       Money money2 = Money.of(new BigDecimal("20"),Currency.getInstance("EUR"));

       assertNotEquals(money1 ,money2);
   }
   @Test void additionOFMoneyWorksAsIntended(){
       Money money1 = Money.of(new BigDecimal("27"),Currency.getInstance("USD"));
       Money money2 = Money.of(new BigDecimal("230"),Currency.getInstance("USD"));

       assertEquals(Money.of(new BigDecimal("257"), Currency.getInstance("USD")), add(money1, money2));
   }

   @Test void additionOfMoneyWithDifferentCurrencyThrowsError(){
       Money money1 = Money.of(new BigDecimal("27"),Currency.getInstance("USD"));
       Money money2 = Money.of(new BigDecimal("23"),Currency.getInstance("EUR"));

       assertThrows(IllegalAdditionException.class,() -> add(money1,money2));

   }

   @Test void normaliseAmountWorks(){
       Money money1 = Money.of(new BigDecimal("15.005"),Currency.getInstance("USD"));
       Money money2 = Money.of(new BigDecimal("15.015"),Currency.getInstance("USD"));

       assertEquals(new BigDecimal("15.00"),money1.getAmount());
       assertEquals(new BigDecimal("15.02"),money2.getAmount());

   }
   @Test void negationWorks(){
        Money money = Money.of(new BigDecimal("5"),Currency.getInstance("GBP"));

        assertEquals(Money.of(new BigDecimal("-5"),Currency.getInstance("GBP")),negate(money));


   }
   @Test void negationTwiceReturnsOriginalValue(){
        Money money = Money.of(new BigDecimal("20"),Currency.getInstance("USD"));
        assertEquals(Money.of(new BigDecimal("20"),Currency.getInstance("USD")),negate(negate(money)));
   }



    }

