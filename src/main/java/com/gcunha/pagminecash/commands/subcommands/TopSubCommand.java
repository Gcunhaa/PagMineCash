package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.commands.SubCommand;
import com.gcunha.pagminecash.data.runnables.TopUpdateBukkitRunnable;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class TopSubCommand extends SubCommand {

    private final TopUpdateBukkitRunnable topBanks;

    public TopSubCommand() {
        super("top", 1, "pagminecash.top", "Ver os jogadores com mais cash do servidor.", "/cash top");
        this.topBanks = new TopUpdateBukkitRunnable();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO:TORNAR CONFIGURAVEL AS MENSAGENS
        commandSender.sendMessage("§a######## TOP CASH ########");
        commandSender.sendMessage("");

        int position = 1;

        for(OfflinePlayer offlinePlayer : topBanks.getTopBanks().keySet()){
            commandSender.sendMessage("§a " + position + "." + offlinePlayer.getName());
            position++;
        }

        commandSender.sendMessage("");

        return true;
    }
}
