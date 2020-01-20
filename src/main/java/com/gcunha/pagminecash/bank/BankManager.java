package com.gcunha.pagminecash.bank;

import com.gcunha.pagminecash.cache.CacheOfflineCash;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class BankManager {

    private HashMap<UUID, Bank> cacheOnline;
    private CacheOfflineCash cacheOffline;


    public BankManager() {
        this.cacheOnline = new HashMap<>();
        this.cacheOffline = new CacheOfflineCash();
    }



    public Bank getBank(UUID uuid){
        //Funcao que retorna o banco

        Bank bank = null;

        if(this.cacheOnline.containsKey(uuid)) {
            bank = this.cacheOnline.get(uuid);
        };

        //Verifica se o objeto esta presente no cacheoffline
        if(cacheOffline.contains(uuid)){

            bank = cacheOffline.get(uuid);

            if(Bukkit.getOnlinePlayers().contains(uuid)) {
                this.cacheOnline.put(uuid,bank);
                this.cacheOffline.remove(uuid);
            }

        }

        //VERIFICAR BANCO DE DADOS E RETORNAR NULO SE NAO TIVER
        return bank;
    }

    public void deleteBankOnlineCache(UUID uuid){
        //Funcao que deleta o banco do cache
        if(this.cacheOnline.containsKey(uuid)) this.cacheOnline.remove(uuid);
    }

    public Bank createBank(UUID uuid){
        //Funcao que cria o banco

        Bank bank = new Bank(uuid, (float) 0);
        if(Bukkit.getOnlinePlayers().contains(uuid)) this.cacheOnline.put(uuid, new Bank(uuid,(float)0) );


        //BANCO DE DADOS
        return bank;
    }

}
