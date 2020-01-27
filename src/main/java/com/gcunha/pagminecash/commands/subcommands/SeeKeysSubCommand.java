package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.commands.SubCommand;
import com.gcunha.pagminecash.data.runnables.TopUpdateBukkitRunnable;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class SeeKeysSubCommand extends SubCommand {

    private final PagMineCash plugin;

    public SeeKeysSubCommand() {
        super("verkeys", 1, "pagminecash.keys.view", "Ver todas as keys existentes e os valores delas.", "/cash verkeys");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO:TORNAR CONFIGURAVEL AS MENSAGENS
        commandSender.sendMessage("§a######## KEYS ########");
        commandSender.sendMessage("");

        for(String key : plugin.getKeyManager().getKeys().keySet()){
            commandSender.sendMessage("§a " + key + " - Valor:" + plugin.getKeyManager().getKey(key).getCash());
        }

        commandSender.sendMessage("");

        return true;
    }
}
