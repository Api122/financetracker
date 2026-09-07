package com.financetracker.infrastructure;

import com.financetracker.domain.Money;
import com.financetracker.domain.Transaction;
import com.financetracker.domain.TransactionType;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.mapper.RowMapper;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Currency;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction map(ResultSet rs, StatementContext ctx) throws SQLException {

        BigDecimal amount = rs.getBigDecimal("amount");
        Currency currency = Currency.getInstance(rs.getString("currency"));

        Money money =  Money.of(amount, currency);

        Instant occuredAt = rs.getTimestamp("occured_at").toInstant();
        String category = rs.getString("category");
        TransactionType type = TransactionType.valueOf(rs.getString("type"));
        return Transaction.of(money,occuredAt,category,type);
    }
}