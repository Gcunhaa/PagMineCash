package com.gcunha.pagminecash.commands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;

public class CommandCash implements CommandExecutor {

    private ArrayList<SubCommand> subCommands;
    private PagMineCash plugin;

    public CommandCash() {
        this.plugin = PagMineCash.getInstance();
        this.subCommands = new ArrayList<>();

        setupCommand();
    }

    private void addSubCommand(SubCommand subCommand){
        subCommands.add(subCommand);
    }



    private void setupCommand(){
        this.plugin.getCommand("cash").setExecutor(this);
    }

    private void showHelp(CommandSender commandSender){

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("cash")){

            if(args.length == 0) {

                if(commandSender instanceof ConsoleCommandSender){
                    showHelp(commandSender);
                    return false;
                }

            }

            String subCommandTitle = args[0];

            for(SubCommand subCommand : subCommands){
                if(!subCommand.getTitle().equalsIgnoreCase(subCommandTitle)) {
                    continue;
                }

                if(!commandSender.hasPermission(subCommand.getPermission())){
                    commandSender.sendMessage("§cVoce nao possui permissao para usar esse comando.");
                    return false;
                }

                if(args.length != subCommand.getArgSize()){
                    commandSender.sendMessage("§cO comando nao foi digitado corretamente, tente: §e" + subCommand.getSyntax());
                    return false;
                }

                try {

                    return subCommand.execute(commandSender,args);

                } catch (Exception exception){

                    commandSender.sendMessage("§cO comando nao foi digitado corretamente, tente: §e" + subCommand.getSyntax());
                    return true;
                }
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(subCommandTitle);
            if(!offlinePlayer.hasPlayedBefore()){
                commandSender.sendMessage("§cNao foi possivel encontrar esse jogador.");
                return true;
            }

            Bank bank = plugin.getBankManager().getBank(offlinePlayer.getUniqueId());
            commandSender.sendMessage("§aSaldo do jogador: " + bank.getCash());
        }

        return true;
    }


}

