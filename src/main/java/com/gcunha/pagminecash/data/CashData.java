package com.gcunha.pagminecash.data;

import com.gcunha.pagminecash.bank.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public interface CashData {

    //Retorna o bank do banco de dados
    Bank getBank(UUID uuid);

    Bank createBank(UUID uuid);

    void updateCash(Bank bank);

    void close(Connection conn, PreparedStatement ps, ResultSet res);

    void closePool();
}
