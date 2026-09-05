package com.financetracker.infrastructuretest;

import com.financetracker.domain.Account;
import org.junit.jupiter.api.Test;
import com.financetracker.infrastructure.InMemoryAccountRepository;

import java.util.*;

import com.financetracker.domain.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryAccountRepositoryTest {

    @Test void accountAddedToAccountHashmap(){
        Account account = Account.of("123", Currency.getInstance("USD"));
        AccountRepository repo = new InMemoryAccountRepository();

        repo.addAccount(account);

        Optional<Account> res = repo.getAccountfromAccountID("123");
        assertTrue(res.isPresent());  // check id
        assertEquals(account,res.get());  // check if account and result same
    }

    @Test void accountFromGetAccountIDWorks(){
        Account account = Account.of("1", Currency.getInstance("GBP"));
        AccountRepository repo = new InMemoryAccountRepository();

        repo.addAccount(account);
        Optional<Account> a1 = repo.getAccountfromAccountID("1");
        assertTrue(a1.isPresent());
        assertEquals(account,a1.get());
    }

    @Test void updateAccountWorks(){
        Account a1 = Account.of("1",Currency.getInstance("USD"));
        Account a2 = Account.of("1",Currency.getInstance("EUR"));
        AccountRepository repo = new InMemoryAccountRepository();

        repo.addAccount(a1);
        repo.updateAccount(a2);

        Optional<Account> result = repo.getAccountfromAccountID("1");
        assertTrue(result.isPresent());
        assertSame(a2,result.get());
        assertNotSame(a1,result.get());
    }
    @Test void deleteAccountWorks(){
        Account account = Account.of("340",Currency.getInstance("EUR"));
        AccountRepository repo = new InMemoryAccountRepository();

        repo.addAccount(account);
        repo.deleteAccount(account.getAccountID());
        Optional<Account> c1 = repo.getAccountfromAccountID("340");
        assertFalse(c1.isPresent());

    }
    @Test void findAllAccountsworks(){
        Account account1 = Account.of("10", Currency.getInstance("USD"));
        Account account2 = Account.of("2",Currency.getInstance("USD"));
        AccountRepository repo = new InMemoryAccountRepository();
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
        Account account = Account.of("1234",Currency.getInstance("USD"));

        AccountRepository repo = new InMemoryAccountRepository();
        Optional<Account> account1 = repo.getAccountfromAccountID("1234");
        assertTrue(account1.isEmpty());

    }

    // additiononid already there

    @Test void additionOnIDAlreadyPresentThrowserror(){
        Account a1 = Account.of("1",Currency.getInstance("USD"));
        Account a2 = Account.of("1",Currency.getInstance("USD"));
        AccountRepository repo = new InMemoryAccountRepository();
        repo.addAccount(a1);

        assertThrows(IllegalArgumentException.class,()-> repo.addAccount(a2));
    }

    @Test void findAllAccountsonEmtpyRepoReturnsEmptyList(){
        AccountRepository repo = new InMemoryAccountRepository();
        assertEquals(new ArrayList<>(), repo.findAllAcoounts());
    }
    @Test void findAllAccountsReturnsANewList(){
        Account account = Account.of("12",Currency.getInstance("USD"));
        AccountRepository repo = new InMemoryAccountRepository();
        repo.addAccount(account);

        List<Account> res = repo.findAllAcoounts();
        res.clear();

        assertSame(1,repo.findAllAcoounts().size());

    }

    @Test void deletionOfNonExistentId(){
        AccountRepository repo = new InMemoryAccountRepository();

        assertThrows(IllegalArgumentException.class,()->repo.deleteAccount("1"));
    }





    }

