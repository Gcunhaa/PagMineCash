package com.gcunha.pagminecash.listener;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.BankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerExitListener implements Listener {

    private final PagMineCash plugin;
    private final BankManager bankManager;

    public PlayerExitListener() {
        this.plugin = PagMineCash.getInstance();
        this.bankManager = plugin.getBankManager();
    }

    @EventHandler
    public void playerExitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();

        bankManager.deleteBankOnlineCache(player.getUniqueId());
    }
}
