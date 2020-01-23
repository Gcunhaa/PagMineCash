package com.gcunha.pagminecash.data.runnables;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TopUpdateBukkitRunnable extends BukkitRunnable {

    private final PagMineCash plugin;
    private ConcurrentHashMap<OfflinePlayer, Bank> topBanks;

    public TopUpdateBukkitRunnable() {
        this.plugin = PagMineCash.getInstance();
        this.runTaskTimerAsynchronously(plugin,1,15 * 60 * 20);
    }

    @Override
    public void run() {
        topBanks = new ConcurrentHashMap<>();

        plugin.getDataManager().getTopBanks().forEach(bank -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(bank.getOwnerUuid());
            topBanks.put(offlinePlayer,bank);
        });

    }

    public ConcurrentHashMap<OfflinePlayer, Bank> getTopBanks() {
        return topBanks;
    }
}
