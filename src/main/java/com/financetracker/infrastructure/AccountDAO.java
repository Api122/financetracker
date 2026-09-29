package com.financetracker.infrastructure;
import com.financetracker.domain.Transaction;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
// Transaction(Money amount , Instant date , String category , TransactionType type)

public interface AccountDAO {

    @SqlUpdate("INSERT INTO accounts(account_id,currency) VALUES (:id,:currency)")
     void insertAccount(@Bind("id") String id, @Bind("currency") String currency);

    @SqlUpdate("INSERT INTO transactions(account_id,type,amount,category,occured_at,currency) VALUES (:id,:type,:amount,:category,:occured_at,:currency)")
    void insertTransaction(@Bind("id") String id,@Bind("type")
    String type, @Bind("amount")
    BigDecimal amount, @Bind("category") String category, @Bind("occured_at")Instant date,
                           @Bind("currency") String currency);


    @SqlUpdate("DELETE FROM accounts WHERE account_id = :id")
     void deleteAccount(@Bind("id") String id);

    @SqlUpdate("UPDATE accounts SET currency = :currency WHERE account_id = :id")
     void updateAccount(@Bind("id") String id,@Bind("currency") String currency);

    @SqlUpdate("DELETE FROM transactions WHERE account_id = :id ")
    void deleteTransactionsFromAccountID(@Bind("id") String id);

    @SqlQuery("SELECT currency FROM accounts WHERE account_id = :id")
    Optional<String> readCurrencyFromAccountID(@Bind("id") String id);

    @SqlQuery("SELECT account_id FROM accounts")
    List<String> findAllaccountsIDs();

    @SqlQuery("SELECT * FROM transactions WHERE account_id=:id ORDER BY id")
    @RegisterRowMapper(TransactionRowMapper.class)
    List<Transaction> findTransactionsByAccountID(@Bind("id") String id);



}
