package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.commands.SubCommand;
import com.gcunha.pagminecash.key.Key;
import org.bukkit.command.CommandSender;

public class DeleteKeySubCommand extends SubCommand {

    private final PagMineCash plugin;

    public DeleteKeySubCommand() {
        super("removerkey", 2, "pagminecash.key.remove", "Remove uma key de cash existente.", "/cash removerkey <key>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS


        String keyString = args[1];

        if(!plugin.getKeyManager().existKey(keyString)) {
            commandSender.sendMessage("§cVoce digitou uma key invalida.");
            return true;
        }

        plugin.getKeyManager().deleteKey(keyString);

        commandSender.sendMessage("§aKey removida com sucesso!");
        return true;
    }
}
