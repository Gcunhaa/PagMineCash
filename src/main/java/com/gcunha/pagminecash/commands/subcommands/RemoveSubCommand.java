package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class RemoveSubCommand extends SubCommand {

    private final PagMineCash plugin;

    public RemoveSubCommand() {
        super("remover", 3, "pagminecash.remove", "Remove uma quantia determinada de cash do jogador.", "/cash remover <jogador> <quantidade>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {
        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS
        String playerName = args[1];
        Float quantity;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

        //Verifica se jogador ja jogou no servidor
        if(!offlinePlayer.hasPlayedBefore()){
            commandSender.sendMessage("§cNao foi possivel encontrar esse jogador.");
            return true;
        }

        //Verifica se o argumento eh de tipo float
        try {
            quantity = Float.parseFloat(args[2]);
        } catch (NumberFormatException e) {
            throw new Exception();
        }

        Bank playerBank = plugin.getBankManager().getBank(offlinePlayer.getUniqueId());
        playerBank.removeCash(quantity);

        commandSender.sendMessage("§aO saldo de cash do jogador %jogador% atual: ".replace("%jogador%",offlinePlayer.getName()) + playerBank.getCash());

        return true;
    }
}
