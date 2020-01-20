package com.gcunha.pagminecash.bank;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.cache.CacheOfflineCash;
import com.gcunha.pagminecash.data.CashData;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class BankManager {

    private final HashMap<UUID, Bank> cacheOnline;
    private final CacheOfflineCash cacheOffline;
    private final PagMineCash plugin;
    private final CashData dataManager;

    public BankManager() {
        this.cacheOnline = new HashMap<>();
        this.cacheOffline = new CacheOfflineCash();
        this.plugin = PagMineCash.getInstance();
        this.dataManager = plugin.getDataManager();
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

        //TODO:VERIFICAR BANCO DE DADOS E RETORNAR NULO SE NAO TIVER
        return bank;
    }

    public void deleteBankOnlineCache(UUID uuid){
        //Funcao que deleta o banco do cache
        if(this.cacheOnline.containsKey(uuid)) this.cacheOnline.remove(uuid);
    }

    public Bank createBank(UUID uuid){
        //Funcao que cria o banco

        try{
            System.out.print(dataManager.getBank(uuid).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if(dataManager.getBank(uuid) == null){
            //TODO:CRIA NO BANCO DE DADOS


        if(Bukkit.getOnlinePlayers().contains(uuid)) this.cacheOnline.put(uuid, null);


        return null;
    }

}
