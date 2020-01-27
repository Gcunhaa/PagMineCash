package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class EditKeySubCommand extends SubCommand {

    private final PagMineCash plugin;

    public EditKeySubCommand() {
        super("editarkey", 3, "pagminecash.key.edit", "Edita uma key já existente para outra quantia de cash.", "/cash editarkey <key> <valor>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS


        String keyString = args[1];
        int quantity;

        if(!plugin.getKeyManager().existKey(keyString)) {
            commandSender.sendMessage("§cVoce digitou uma key invalida.");
            return true;
        }

        //Verifica se o argumento eh de tipo int
        try {
            quantity = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new Exception();
        }

        if(quantity < 0){
            commandSender.sendMessage("§cValor invalido.");
            return true;
        }

        plugin.getKeyManager().getKey(keyString).setCash(quantity);

        commandSender.sendMessage("§aKey editada com sucesso!");
        return true;
    }
}
