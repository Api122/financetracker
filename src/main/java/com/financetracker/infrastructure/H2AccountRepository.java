package com.financetracker.infrastructure;

import com.financetracker.domain.Account;
import com.financetracker.domain.AccountRepository;
import com.financetracker.domain.Transaction;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;
import java.util.Currency;
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
        Optional<String> c = dao.readCurrencyFromAccountID(accountID);
        if(c.isEmpty()){return Optional.empty();}
        String currencyCode = c.get();
        Account account = Account.of(accountID, Currency.getInstance(currencyCode));

        List<Transaction> transactions = dao.findTransactionsByAccountID(accountID);
        for(Transaction t : transactions){account.addNewTransaction(t);}

        return Optional.of(account);

    }

    @Override
    public void updateAccount(Account account) {
        Optional<String> c = dao.readCurrencyFromAccountID(account.getAccountID());
        if(c.isEmpty()){throw new IllegalArgumentException("cannot update empty account");}
        dao.updateAccount(account.getAccountID(),account.getCurrency().getCurrencyCode());
        dao.deleteTransactionsFromAccountID(account.getAccountID());

        for(Transaction t : account.getTransactions()){
            dao.insertTransaction(account.getAccountID(),t.getType().toString(),t.getAmount().getAmountmoney(),
                    t.getCategory(),t.getDate(),t.getAmount().getCurrency().getCurrencyCode());
        }

    }

    @Override
    public void deleteAccount(String accountID) {
        Optional<String> c = dao.readCurrencyFromAccountID(accountID);
        if(c.isEmpty()){throw new IllegalArgumentException("cannot delete non-existent id");}
        dao.deleteTransactionsFromAccountID(accountID);
        dao.deleteAccount(accountID);

    }

    @Override
    public List<Account> findAllAcoounts() {
        ArrayList<Account> res = new ArrayList<>();
        for(String id : dao.findAllaccountsIDs()){
            Optional<Account> account = getAccountfromAccountID(id);
            account.ifPresent(res::add);
        }
        return res;
    }
}





