package com.financetracker.infrastructuretest;

import com.financetracker.domain.*;
import com.financetracker.infrastructure.H2AccountRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class H2AccountRepositoryTest {


    private H2AccountRepository freshRepo() {
        try {
            Jdbi jdbi = Jdbi.create("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.useHandle(handle -> handle.execute(Files.readString(Path.of("src/main/resources/schema.sql"))));
            return new H2AccountRepository(jdbi);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test database", e);

        }
    }

    @Test
    void accountAddedToAccountHashmap(){
        Account account = Account.of("123", Currency.getInstance("USD"));
        AccountRepository repo = freshRepo();

        repo.addAccount(account);

        Optional<Account> res = repo.getAccountfromAccountID("123");
        assertTrue(res.isPresent());  // check id
        assertEquals(account,res.get());  // check if account and result same
    }

    @Test void accountFromGetAccountIDWorks(){
        Account account = Account.of("1", Currency.getInstance("GBP"));
        AccountRepository repo = freshRepo();

        repo.addAccount(account);
        Optional<Account> a1 = repo.getAccountfromAccountID("1");
        assertTrue(a1.isPresent());
        assertEquals(account,a1.get());
    }

    @Test void updateAccountWorks(){
        Account a1 = Account.of("1",Currency.getInstance("USD"));
        Account a2 = Account.of("1",Currency.getInstance("EUR"));
        AccountRepository repo = freshRepo();

        repo.addAccount(a1);
        repo.updateAccount(a2);


        Optional<Account> result = repo.getAccountfromAccountID("1");
        assertTrue(result.isPresent());
        assertEquals(a2,result.get());
        assertNotEquals(a1,result.get());

    }
    @Test void deleteAccountWorks(){
        Account account = Account.of("340",Currency.getInstance("EUR"));
        AccountRepository repo = freshRepo();

        repo.addAccount(account);
        repo.deleteAccount(account.getAccountID());
        Optional<Account> c1 = repo.getAccountfromAccountID("340");
        assertFalse(c1.isPresent());

    }
    @Test void findAllAccountsworks(){
        Account account1 = Account.of("10", Currency.getInstance("USD"));
        Account account2 = Account.of("2",Currency.getInstance("USD"));
        AccountRepository repo = freshRepo();
        repo.addAccount(account1);
        repo.addAccount(account2);
        List<Account> result = repo.findAllAcoounts();

        assertEquals(2,result.size());
        assertTrue(result.contains(account1));
        assertTrue(result.contains(account2));



    }

    // edge cases

    // getaccountIDedgecase

    @Test void gettingAccountIDonIDNeverAdded(){

        AccountRepository repo = freshRepo();
        Optional<Account> account1 = repo.getAccountfromAccountID("1234");
        assertTrue(account1.isEmpty());

    }

    // additiononid already there

    @Test void additionOnIDAlreadyPresentThrowserror(){
        Account a1 = Account.of("1",Currency.getInstance("USD"));
        Account a2 = Account.of("1",Currency.getInstance("USD"));
        AccountRepository repo = freshRepo();
        repo.addAccount(a1);

        assertThrows(IllegalArgumentException.class,()-> repo.addAccount(a2));
    }

    @Test void findAllAccountsonEmtpyRepoReturnsEmptyList(){
        AccountRepository repo = freshRepo();
        assertEquals(new ArrayList<>(), repo.findAllAcoounts());
    }
    @Test void findAllAccountsReturnsANewList(){
        Account account = Account.of("12",Currency.getInstance("USD"));
        AccountRepository repo = freshRepo();
        repo.addAccount(account);

        List<Account> res = repo.findAllAcoounts();
        res.clear();

        assertSame(1,repo.findAllAcoounts().size());

    }

    @Test void deletionOfNonExistentId(){
        AccountRepository repo = freshRepo();


        assertThrows(IllegalArgumentException.class,()->repo.deleteAccount("1"));
    }

    @Test
    void transactionsSurviveRoundTrip() {
        AccountRepository repo = freshRepo();
        Currency usd = Currency.getInstance("USD");

        Account a = Account.of("1", usd);
        a.addNewTransaction(Transaction.of(
                Money.of(new BigDecimal("50.00"), usd),
                Instant.parse("2024-01-15T09:00:00Z"), "salary", TransactionType.INCOME));
        a.addNewTransaction(Transaction.of(
                Money.of(new BigDecimal("12.50"), usd),
                Instant.parse("2024-01-16T12:30:00Z"), "food", TransactionType.EXPENSE));

        repo.addAccount(a);

        Optional<Account> result = repo.getAccountfromAccountID("1");
        assertTrue(result.isPresent());
        assertEquals(a, result.get());
    }

    @Test
    void updateReplacesTransactions() {
        AccountRepository repo = freshRepo();
        Currency usd = Currency.getInstance("USD");

        Account a1 = Account.of("1", usd);
        a1.addNewTransaction(Transaction.of(
                Money.of(new BigDecimal("50.00"), usd),
                Instant.parse("2024-01-15T09:00:00Z"), "salary", TransactionType.INCOME));

        Account a2 = Account.of("1", usd);
        a2.addNewTransaction(Transaction.of(
                Money.of(new BigDecimal("20.00"), usd),
                Instant.parse("2024-02-01T10:00:00Z"), "rent", TransactionType.EXPENSE));
        a2.addNewTransaction(Transaction.of(
                Money.of(new BigDecimal("5.00"), usd),
                Instant.parse("2024-02-02T11:00:00Z"), "coffee", TransactionType.EXPENSE));

        repo.addAccount(a1);
        repo.updateAccount(a2);

        Optional<Account> result = repo.getAccountfromAccountID("1");
        assertTrue(result.isPresent());
        assertEquals(a2, result.get());
        assertNotEquals(a1, result.get());
    }






}

