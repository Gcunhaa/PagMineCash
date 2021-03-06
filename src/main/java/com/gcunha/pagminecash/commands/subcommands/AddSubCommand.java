package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class AddSubCommand extends SubCommand {

    private final PagMineCash plugin;

    public AddSubCommand() {
        super("adicionar", 3, "pagminecash.add", "Adiciona uma quantia determinada de cash para o jogador.", "/cash adicionar <jogador> <quantidade>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS
        String playerName = args[1];
        int quantity;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

        //Verifica se jogador ja jogou no servidor
        if(!offlinePlayer.hasPlayedBefore()){
            commandSender.sendMessage("§cNao foi possivel encontrar esse jogador.");
            return true;
        }

        //Verifica se o argumento eh de tipo int
        try {
            quantity = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new Exception();
        }

        Bank playerBank = plugin.getBankManager().getBank(offlinePlayer.getUniqueId());
        playerBank.addCash(quantity);

        commandSender.sendMessage("§aO saldo de cash do jogador %jogador% foi atual: ".replace("%jogador%",offlinePlayer.getName()) + playerBank.getCash());

        return true;
    }
}
