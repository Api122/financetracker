package com.financetracker.infrastructure;

import com.financetracker.domain.Account;
import com.financetracker.domain.AccountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    HashMap<String,Account> accountHashMap = new HashMap<>();
     // basic crud operations on account useful for testing with databases for later
    @Override
    public void addAccount(Account account) {
        if(accountHashMap.containsKey(account.getAccountID())){throw new IllegalArgumentException("Cannot overwrite existing id");}
        accountHashMap.put(account.getAccountID(), account);
    }

    @Override
    public Optional<Account> getAccountfromAccountID(String accountID) {
        return Optional.ofNullable(accountHashMap.get(accountID));}


    @Override
    public void updateAccount(Account updateaccount) {
        accountHashMap.put(updateaccount.getAccountID(),updateaccount);

    }

    @Override
    public void deleteAccount(String accountID) {
        if(!accountHashMap.containsKey(accountID)){throw new IllegalArgumentException("Cannot delete a non existent ID");}
        accountHashMap.remove(accountID);

    }

    @Override
    public List<Account> findAllAcoounts() {
        return new ArrayList<>(accountHashMap.values());
    }
}
