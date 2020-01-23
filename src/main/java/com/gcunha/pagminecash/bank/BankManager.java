package com.gcunha.pagminecash.bank;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.cache.CacheOfflineCash;
import com.gcunha.pagminecash.data.CashData;
import com.gcunha.pagminecash.data.runnables.TopUpdateBukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

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

            if(Bukkit.getOnlinePlayers().contains(uuid) && !this.cacheOnline.containsKey(uuid)) {
                this.cacheOnline.put(uuid,bank);
                this.cacheOffline.remove(uuid);
            }

        }


        if(bank == null){
            //VERIFICA BANCO DE DADOS E RETORNAR NULO SE NAO TIVER
            bank = dataManager.getBank(uuid);
        }


        //Se jogador estiver offline adiciona no cacheOffline
        if(Bukkit.getPlayer(uuid) == null && bank != null ) cacheOffline.add(bank);

        return bank;
    }

    public void deleteBankOnlineCache(UUID uuid){
        //Funcao que deleta o banco do cache
        if(this.cacheOnline.containsKey(uuid)) this.cacheOnline.remove(uuid);
    }

    public Bank createBank(UUID uuid){
        //Funcao que cria o banco

        Bank bank = null;

        //Verifica se o jogador esta no cacheOffline
        if(this.cacheOffline.contains(uuid)){
            bank = this.cacheOffline.get(uuid);
        }else{
            bank = this.dataManager.getBank(uuid);
        }

        if(bank == null){
            this.dataManager.createBank(uuid);
            bank = new Bank(uuid);
        }

        //Verifica se o jogador esta online ou nao, posteriormente adiciona ao chache de acordo com o estado dele
        if(Bukkit.getPlayer(uuid) != null) {

            if(this.cacheOffline.contains(uuid)){
                this.cacheOffline.remove(uuid);
            }

            this.cacheOnline.put(uuid, bank);
        }else{
            this.cacheOffline.add(bank);
        }


        return bank;
    }

    public HashMap<UUID, Bank> getCacheOnline() {
        return cacheOnline;
    }
}
