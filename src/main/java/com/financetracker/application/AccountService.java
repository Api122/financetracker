package com.financetracker.application;


import com.financetracker.domain.Account;
import com.financetracker.domain.AccountRepository;
import com.financetracker.domain.Money;
import com.financetracker.domain.Transaction;


import java.util.Currency;
import java.util.List;

public class AccountService {
    private final AccountRepository repository;


    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account createAccount(String id, Currency c) {
        if (repository.getAccountfromAccountID(id).isPresent()) {
            throw new IllegalAccountCreation("Account with this id already made id is:" + id);
        }
        Account a = Account.of(id,c);
        repository.addAccount(a);
        return a;
    }

    public List<Account> listAccounts(){
        return repository.findAllAcoounts();
    }

    public void recordTransaction(String id,Transaction t){
       Account a = getAccount(id);
       a.addNewTransaction(t);
       repository.updateAccount(a);
    }

    public List<Transaction> getTransactions(String id){
        Account b = getAccount(id);
        return b.getTransactions();
    }

    public Account getAccount(String id) {
        return repository.getAccountfromAccountID(id).orElseThrow(()-> new IllegalAccountValue("No account exists id value is:" + id));

    }

    public Money getBalance (String id){
        Account a = getAccount(id);
        return a.getBalance();
    }






}
