package com.gcunha.pagminecash.cache;

import com.gcunha.pagminecash.bank.Bank;

public class CacheOfflineCashObject {

    //Tempo em que vai ser deletado
    private long deleteTime;
    private Bank bank;

    public CacheOfflineCashObject(Bank bank, long timeStored) {
        this.bank = bank;
        this.deleteTime = System.currentTimeMillis() + timeStored;
    }

    public void addTime(long extraTime){
        //Funcao que adiciona mais tempo de vida para o objeto no cache

        this.deleteTime = this.deleteTime + extraTime;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public Bank getBank() {
        return bank;
    }
}
