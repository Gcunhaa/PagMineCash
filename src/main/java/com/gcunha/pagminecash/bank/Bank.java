package com.gcunha.pagminecash.bank;

import com.gcunha.pagminecash.PagMineCash;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Bank {

    private UUID ownerUuid;
    private Float cash;

    public Bank(UUID ownerUuid, Float cash) {
        this.ownerUuid = ownerUuid;
        this.cash = cash;
    }

    public Bank(UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
        this.cash = (float) 0.0;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public Float getCash() {
        return cash;
    }

    public void setCash(Float quantity){
        //Funcao que atualizar o valor de cash no banco de dados
        if(quantity < 0) {
            quantity = (float) 0;
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

    public void addCash(Float quantity){
        //Funcao que adiciona cash ao jogador
        setCash(this.cash + quantity);
    }

    public void removeCash(Float quantity){
        //Funcao que remove cash do jogador

        Float cash = this.cash;

        setCash(this.cash - quantity);
    }


}
