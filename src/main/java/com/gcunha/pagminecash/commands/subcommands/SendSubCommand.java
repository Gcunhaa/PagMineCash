package com.gcunha.pagminecash.commands.subcommands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SendSubCommand extends SubCommand {

    private final PagMineCash plugin;

    public SendSubCommand() {
        super("enviar", 3, "pagminecash.send", "Envia uma determinada quantia de cash para um jogador.", "/cash enviar <jogador> <quantidade>");
        this.plugin = PagMineCash.getInstance();
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) throws Exception {

        if(commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("§cEsse comando nao pode ser utilizado pelo console.");
            return true;
        }

        //TODO: DEIXAR AS MENSAGENS CONFIGURAVEIS
        String playerName = args[1];
        Float quantity;

        Player player = (Player) commandSender;
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);

        //Verifica se jogador ja jogou no servidor
        if(!targetPlayer.hasPlayedBefore()){
            commandSender.sendMessage("§cNao foi possivel encontrar esse jogador.");
            return true;
        }

        //Verifica se o argumento eh de tipo float
        try {
            quantity = Float.parseFloat(args[2]);
        } catch (NumberFormatException e) {
            throw new Exception();
        }

        if (quantity < 0) throw new Exception();

        Bank playerBank = plugin.getBankManager().getBank(player.getUniqueId());
        Bank targetBank = plugin.getBankManager().getBank(targetPlayer.getUniqueId());

        if(playerBank.getCash() > quantity){
            playerBank.removeCash(quantity);
            targetBank.addCash(quantity);
            commandSender.sendMessage("§aVoce enviou %cash% ao jogador: %jogador% : ".replace("%jogador%",targetPlayer.getName()).replace("%cash%",quantity.toString()));
        }else{
            commandSender.sendMessage("§cSaldo insuficiente.");
        }



        return true;
    }
}
