package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.Console;

public class ActivateKeySubCommand extends SubCommand {

    private final PagMineCash plugin;

    public ActivateKeySubCommand() {
        super("ativar", 2, "pagminecash.key.activate", "Ativa uma key de cash.", "/cash ativar <key>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS

        if(commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("§cEsse comando nao pode ser executado pelo console.");

            return false;
        }

        String keyString = args[1];

        if(!plugin.getKeyManager().existKey(keyString)) {
            commandSender.sendMessage("§cVoce digitou uma key invalida.");
            return true;
        }

        Player player = (Player) commandSender;

        plugin.getKeyManager().useKey(player,keyString);

        player.sendMessage("§aKey ativada com sucesso!");
        return true;
    }
}
