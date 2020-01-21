package com.gcunha.pagminecash.bank;

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
        this.cash = quantity;
    }

    public void addCash(Float quantity){
        //Funcao que adiciona cash ao jogador
        setCash(this.cash + quantity);
    }

    public void removeCash(Float quantity){
        //Funcao que remove cash do jogador

        Float cash = this.cash;

        if(quantity > cash) {
            Float delta = quantity - cash;
            quantity = quantity - delta;
        }

        setCash(this.cash - quantity);
    }


}
