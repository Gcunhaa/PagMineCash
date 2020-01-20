package com.gcunha.pagminecash.listener;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.BankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PagMineCash plugin;
    private final BankManager bankManager;

    public PlayerJoinListener() {
        this.plugin = PagMineCash.getInstance();
        this.bankManager = plugin.getBankManager();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        bankManager.createBank(player.getUniqueId());

    }

}
