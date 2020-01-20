package com.gcunha.pagminecash.data;

import com.gcunha.pagminecash.bank.Bank;

import java.util.UUID;

public interface CashData {

    //Retorna o bank do banco de dados
    Bank getBank(UUID uuid);

    Bank createBank(UUID uuid);

    void updateCash(Bank bank);


}
