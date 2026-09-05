package com.financetracker.domaintest;

import com.financetracker.domain.Account;
import com.financetracker.domain.Money;
import com.financetracker.domain.Transaction;
import com.financetracker.domain.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {


    @Test void validAccountInitialization(){

        Account a1 =  Account.of("1234", Currency.getInstance("USD"));

        assertAll(
                ()->assertEquals("1234", a1.getAccountID()),
                () -> assertEquals(Currency.getInstance("USD"),a1.getCurrency())
        );
    }

    // null checks in constructor


    @Test void accountThrowsNullExceptionWhenAccountIDNull(){

        assertThrows(NullPointerException.class,()->Account.of(null,Currency.getInstance("EUR")));
    }

    @Test void accountThrowsNullExceptionWhenCurrencyIsNull(){
        assertThrows(NullPointerException.class,()->Account.of("12",null));
    }

    // null checks in methods of account

    @Test void throwsNullExceptionWhenInputisNulltoAddition(){
       Account a1 = Account.of("1",Currency.getInstance("USD"));

        assertThrows(NullPointerException.class,()-> a1.addNewTransaction(null));

    }

    @Test void throwsNullExceptionWhenInputisNulltoRemoval(){
        Account a1 = Account.of("3",Currency.getInstance("EUR"));

        assertThrows(NullPointerException.class,()->a1.removeTransaction(null));

    }

    // illegal arguments for the transactionadding and subtraction and accountID

    @Test void throwsIllegalArguemntWhenIDisBlank(){

        assertThrows(IllegalArgumentException.class,()->Account.of("",Currency.getInstance("USD")));
    }


    @Test void throwsIllegalArguementWhenInputDoesNotMatchAccountCurrencyaddition(){

        Account a1 = Account.of("4",Currency.getInstance("GBP"));
        Transaction t = Transaction.of(Money.of(new BigDecimal("10"),Currency.getInstance("USD")),
                Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);

        assertThrows(IllegalArgumentException.class,()->a1.addNewTransaction(t));
    }

    @Test void throwsIllegalArguementWhenInputDoesNotMatchAccountCurrencyremoval(){

        Account a1 = Account.of("4",Currency.getInstance("GBP"));
        Transaction t = Transaction.of(Money.of(new BigDecimal("10"),Currency.getInstance("USD")),
                Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);

        assertThrows(IllegalArgumentException.class,()->a1.removeTransaction(t));
    }

    @Test void transactionHistoryEqualToTransactionWhenAdding(){
        Account a1 = Account.of("1",Currency.getInstance("EUR"));
        Transaction t = Transaction.of(Money.of(new BigDecimal("10"),Currency.getInstance("EUR")),
                Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);
        a1.addNewTransaction(t);

          assertEquals(a1.getTransactions(),a1.getTransactionHistory());

    }

    @Test void transactionHistoryEqualToTransactionWhenremoving(){
        Account a1 = Account.of("1",Currency.getInstance("EUR"));
        Transaction t = Transaction.of(Money.of(new BigDecimal("10"),Currency.getInstance("EUR")),
                Instant.parse("2025-10-05T00:00:00Z"),"Misc",
                TransactionType.EXPENSE);
        a1.addNewTransaction(t);
        a1.removeTransaction(t);
        assertNotEquals(a1.getTransactions(),a1.getTransactionHistory());
    }


    // getBalance checks

    @Test void getBalanceWorkingWithOnlyExpense(){
        Account account = Account.of("12345",Currency.getInstance("USD"));
        Money m1 = Money.of(new BigDecimal("20"),Currency.getInstance("USD"));
        Transaction t1 = Transaction.of(m1,Instant.parse("2025-10-05T00:00:00Z"),"Gas",TransactionType.EXPENSE);
        Money m2 = Money.of(new BigDecimal("10"),Currency.getInstance("USD"));
        Transaction t2 = Transaction.of(m2,Instant.now(),"Sweets",TransactionType.EXPENSE);

        account.addNewTransaction(t1);
        account.addNewTransaction(t2);

        Money expected = Money.of(new BigDecimal("-30"),Currency.getInstance("USD"));

        assertEquals(expected,account.getBalance());

    }


    @Test void getBalanceWorkingWithOnlyIncome(){
        Account account = Account.of("12345",Currency.getInstance("USD"));
        Money m1 = Money.of(new BigDecimal("2000"),Currency.getInstance("USD"));
        Transaction t1 = Transaction.of(m1,Instant.parse("2025-10-05T00:00:00Z"),"Job",TransactionType.INCOME);
        Money m2 = Money.of(new BigDecimal("10"),Currency.getInstance("USD"));
        Transaction t2 = Transaction.of(m2,Instant.now(),"Refund",TransactionType.INCOME);

        account.addNewTransaction(t1);
        account.addNewTransaction(t2);

        Money expected = Money.of(new BigDecimal("2010"),Currency.getInstance("USD"));

        assertEquals(expected,account.getBalance());

    }

    @Test void getBalanceWorkingWithIncomeAndExpense(){
        Account account = Account.of("12345",Currency.getInstance("USD"));
        Money m1 = Money.of(new BigDecimal("2000"),Currency.getInstance("USD"));
        Transaction t1 = Transaction.of(m1,Instant.parse("2025-10-05T00:00:00Z"),"Job",TransactionType.INCOME);
        Money m2 = Money.of(new BigDecimal("20"),Currency.getInstance("USD"));
        Transaction t2 = Transaction.of(m2,Instant.now(),"Refund",TransactionType.EXPENSE);

        account.addNewTransaction(t1);
        account.addNewTransaction(t2);

        Money expected = Money.of(new BigDecimal("1980"),Currency.getInstance("USD"));

        assertEquals(expected,account.getBalance());

    }

    @Test
    void getTransactionsReturnsUnmodifiableList() {
        Account account = Account.of("ACC123", Currency.getInstance("USD"));

        assertThrows(UnsupportedOperationException.class, () ->
                account.getTransactions().add(null)
        );
    }

    @Test
    void getTransactionHistoryReturnsUnmodifiableList() {
        Account account = Account.of("ACC123", Currency.getInstance("USD"));

        assertThrows(UnsupportedOperationException.class, () ->
                account.getTransactionHistory().add(null)
        );
    }



}

