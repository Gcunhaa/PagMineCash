package com.gcunha.pagminecash.cache;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CacheOfflineCash extends BukkitRunnable {

    //tempo para exclusao do objeto do cache em minutos
    private long timeStored = 10 * 60000;
    private ConcurrentHashMap<UUID,CacheOfflineCashObject> cacheOfflineCash ;
    private PagMineCash plugin;

    public CacheOfflineCash() {
        this.cacheOfflineCash = new ConcurrentHashMap<>();
        this.plugin = PagMineCash.getInstance();
        runTaskTimerAsynchronously(plugin,1,1200 );
    }

    @Override
    public void run() {

        for(CacheOfflineCashObject cacheObject : cacheOfflineCash.values()){
            //Verifica se o objeto deve ser removido da cache
            if(cacheObject.getDeleteTime() <= System.currentTimeMillis()){
                remove(cacheObject.getBank().getOwnerUuid());
            }
        }

    }

    public Bank get(UUID uuid){
        //Funcao que retorna o bank presente no cache

        if(this.cacheOfflineCash.containsKey(uuid)) return this.cacheOfflineCash.get(uuid).getBank();

        return null;
    }

    public void add(Bank bank){
        //Funcao que adiciona o objeto ao cache

        UUID uuid = bank.getOwnerUuid();
        if(this.cacheOfflineCash.containsKey(uuid)) return;
        this.cacheOfflineCash.put(uuid, new CacheOfflineCashObject(bank,this.timeStored));
    }

    public void remove(UUID uuid){
        //Funcao que remove o banco do cache

        synchronized (cacheOfflineCash){
            this.cacheOfflineCash.remove(uuid);
        };
    }

    public Boolean contains(UUID uuid){
        return this.cacheOfflineCash.containsKey(uuid);
    }
}
