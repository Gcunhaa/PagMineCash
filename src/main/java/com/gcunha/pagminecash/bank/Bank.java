package com.gcunha.pagminecash.bank;

import com.gcunha.pagminecash.PagMineCash;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Bank {

    private UUID ownerUuid;
    private int cash;

    public Bank(UUID ownerUuid, int cash) {
        this.ownerUuid = ownerUuid;
        this.cash = cash;
    }

    public Bank(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
        this.cash = 0;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int quantity){
        //Funcao que atualizar o valor de cash no banco de dados
        if(quantity < 0) {
            quantity = 0;
        }

        this.cash = quantity;
        Bank bank = this;

        new BukkitRunnable() {
            @Override
            public void run() {
                PagMineCash.getInstance().getDataManager().updateCash(bank);
            }
        }.runTaskAsynchronously(PagMineCash.getInstance());
    }

    public void addCash(int quantity){
        //Funcao que adiciona cash ao jogador
        setCash(this.cash + quantity);
    }

    public void removeCash(int quantity){
        //Funcao que remove cash do jogador

        setCash(this.cash - quantity);
    }


}
