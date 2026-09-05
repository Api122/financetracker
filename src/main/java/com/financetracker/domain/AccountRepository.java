package com.financetracker.domain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    void addAccount(Account account);
    Optional<Account> getAccountfromAccountID(String  accountID);
    void updateAccount(Account account);
    void deleteAccount(String accountID);
    List<Account> findAllAcoounts();
}
