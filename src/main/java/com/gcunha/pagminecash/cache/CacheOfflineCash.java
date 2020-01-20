package com.gcunha.pagminecash.cache;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CacheOfflineCash extends BukkitRunnable {

    //tempo para exclusao do objeto do cache em minutos
    private long timeStored = 15 * 6000;
    private HashMap<UUID,CacheOfflineCashObject> cacheOfflineCash ;
    private PagMineCash plugin;

    public CacheOfflineCash() {
        this.cacheOfflineCash = new HashMap<>();
        this.plugin = PagMineCash.getInstance();
        runTaskTimerAsynchronously(plugin,1,60000);
    }

    @Override
    public void run() {
        for(CacheOfflineCashObject cashObject : cacheOfflineCash.values()){
            //Verifica se o objeto deve ser removido da cache
            if(cashObject.getDeleteTime() == System.currentTimeMillis()) this.cacheOfflineCash.remove(cashObject);
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

        this.cacheOfflineCash.remove(uuid);
    }

    public Boolean contains(UUID uuid){
        return this.cacheOfflineCash.containsKey(uuid);
    }
}
