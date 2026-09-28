package com.financetracker.domaintest;

import com.financetracker.domain.Money;
import com.financetracker.domain.Transaction;
import com.financetracker.domain.TransactionType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

public class TransactionTest {


    @Test void transactionInitialiseWorksWithExpense() {

        Transaction t = Transaction.of(Money.of(new BigDecimal("20"), Currency.getInstance("USD")), Instant.parse("2021-05-01T00:00:00Z"),
                "Bill", TransactionType.EXPENSE);

        assertAll(
                () -> assertEquals(Money.of(new BigDecimal("20"), Currency.getInstance("USD")), t.getAmount()),
                () -> assertEquals(Instant.parse("2021-05-01T00:00:00Z"),t.getDate()),
                () -> assertEquals("Bill", t.getCategory()),
                () -> assertEquals(TransactionType.EXPENSE,t.getType())
        );
    }
    @Test void transactionInitialiseWorksWithIncome(){
        Transaction t = Transaction.of(Money.of(new BigDecimal("20"), Currency.getInstance("USD")), Instant.parse("2021-05-01T00:00:00Z"),
                "Bill", TransactionType.INCOME);

        assertAll(
                () -> assertEquals(Money.of(new BigDecimal("20"), Currency.getInstance("USD")), t.getAmount()),
                () -> assertEquals(Instant.parse("2021-05-01T00:00:00Z"),t.getDate()),
                () -> assertEquals("Bill", t.getCategory()),
                () -> assertEquals(TransactionType.INCOME,t.getType())
        );

    }
    // null tests

    @Test  void nullAmountThrowsError(){
        assertThrows(NullPointerException.class, ()->
                Transaction.of(null, Instant.parse("2022-09-05T00:00:00Z"),
                        "Bill", TransactionType.EXPENSE));

    }

    @Test void nullDateThrowsError(){
        assertThrows(NullPointerException.class, ()->Transaction.of(Money.of(new BigDecimal("1500"),Currency.getInstance("GBP")), null,
                "Mortgage", TransactionType.EXPENSE));
    }

    @Test void nullCategoryThrowsError(){
        assertThrows(NullPointerException.class,()->Transaction.of(Money.of(new BigDecimal("1500"),Currency.getInstance("GBP")),
                Instant.parse("2023-03-18T00:00:00Z"),
                null, TransactionType.EXPENSE));
    }
    @Test void nullTypeThrowsError(){
        assertThrows(NullPointerException.class, () -> Transaction.of(Money.of(new BigDecimal("100"),Currency.getInstance("EUR")),
                Instant.parse("2022-09-05T00:00:00Z"),"Misc",null));
    }

    //empty tests

    @Test void emptyCategoryThrowsError(){
        assertThrows(IllegalArgumentException.class,() ->Transaction.of(Money.of(new BigDecimal("100"),Currency.getInstance("EUR")),
                Instant.parse("2022-09-05T00:00:00Z"),"",TransactionType.EXPENSE));
    }



    // general tests

    @Test void twoIdenticalTransactionsAreNotTheSame(){
       Transaction t1 = Transaction.of(Money.of(new BigDecimal("10"),Currency.getInstance("USD")),Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);

        Transaction t2 = Transaction.of(Money.of(new BigDecimal("10"),Currency.getInstance("USD")),Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);

        assertNotSame(t1,t2);

    }

    @Test void negativeAmountInTransactionThrowsError(){
        assertThrows(IllegalArgumentException.class,() ->Transaction.of(Money.of(new BigDecimal("-10"),Currency.getInstance("USD")),Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE));

    }

   @Test void zeroAmountAccepted(){
        Transaction t = Transaction.of(Money.of(new BigDecimal("0"),Currency.getInstance("USD")),Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);

        assertEquals(0, new BigDecimal("0").compareTo(t.getAmount().getAmountmoney()));
   }

    @Test void toStringContainsKeyFields(){
        Transaction t = Transaction.of(
                Money.of(new BigDecimal("20"), Currency.getInstance("USD")),
                Instant.parse("2021-05-01T00:00:00Z"), "Bill", TransactionType.EXPENSE);

        String result = t.toString();

        assertAll(
                () -> assertTrue(result.contains("Bill")),
                () -> assertTrue(result.contains("EXPENSE"))
        );
    }




}

