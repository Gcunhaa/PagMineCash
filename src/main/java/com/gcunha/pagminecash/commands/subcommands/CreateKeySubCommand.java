package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.commands.SubCommand;
import com.gcunha.pagminecash.key.Key;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CreateKeySubCommand extends SubCommand {

    private final PagMineCash plugin;

    public CreateKeySubCommand() {
        super("gerarkey", 2, "pagminecash.key.generate", "Gerar uma key de cash.", "/cash gerarkey <cash>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS


        int quantity = 0;

        //Verifica se o argumento eh de tipo int
        try {
            quantity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new Exception();
        }

        if(quantity < 0){
            commandSender.sendMessage("§cValor invalido.");
            return true;
        }

        Key key = plugin.getKeyManager().createKey(quantity);

        commandSender.sendMessage("§aKey criada com sucesso: " + key.getKeyString());
        return true;
    }
}
