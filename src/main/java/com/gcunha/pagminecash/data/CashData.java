package com.gcunha.pagminecash.data;

import com.gcunha.pagminecash.bank.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.UUID;

public interface CashData {

    //Retorna o bank do banco de dados
    Bank getBank(UUID uuid);

    ArrayList<Bank> getTopBanks();

    void createBank(UUID uuid);

    void updateCash(Bank bank);

    void close(Connection conn, PreparedStatement ps, ResultSet res);

    void closePool();
}
