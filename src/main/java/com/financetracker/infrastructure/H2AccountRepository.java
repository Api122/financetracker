package com.financetracker.infrastructure;

import com.financetracker.domain.Account;
import com.financetracker.domain.AccountRepository;
import com.financetracker.domain.Transaction;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class H2AccountRepository implements AccountRepository {
    private final AccountDAO dao;

    public H2AccountRepository(Jdbi jdbi) {
        jdbi.registerRowMapper(new TransactionRowMapper());
        this.dao = jdbi.onDemand(AccountDAO.class);
    }

    @Override
    public void addAccount(Account account) {
            Optional<String> c = dao.readCurrencyFromAccountID(account.getAccountID());
            if(c.isPresent()){throw new IllegalArgumentException("cannot overwrite existing id");}
            dao.insertAccount(account.getAccountID(), account.getCurrency().getCurrencyCode());
            for(Transaction t : account.getTransactions()){
                dao.insertTransaction(account.getAccountID(),t.getType().toString(),t.getAmount().getAmountmoney(),t.getCategory(),t.getDate(),t.getAmount().getCurrency().getCurrencyCode());
            }


    }

    @Override
    public Optional<Account> getAccountfromAccountID(String accountID) {
        return Optional.empty();
    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(String accountID) {
        dao.deleteTransactionsFromAccountID(accountID);
        dao.deleteAccount(accountID);

    }

    @Override
    public List<Account> findAllAcoounts() {
        return List.of();
    }
}





