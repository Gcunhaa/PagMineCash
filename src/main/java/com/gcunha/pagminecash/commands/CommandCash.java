package com.gcunha.pagminecash.commands;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.bank.BankManager;
import com.gcunha.pagminecash.commands.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CommandCash implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands;
    private final PagMineCash plugin;
    private final BankManager bankManager;

    public CommandCash() {
        this.plugin = PagMineCash.getInstance();
        this.subCommands = new ArrayList<>();
        this.bankManager = plugin.getBankManager();

        addSubCommand(new TopSubCommand());
        addSubCommand(new SetSubCommand());
        addSubCommand(new AddSubCommand());
        addSubCommand(new RemoveSubCommand());
        addSubCommand(new SendSubCommand());
        addSubCommand(new ActivateKeySubCommand());
        addSubCommand(new CreateKeySubCommand());
        addSubCommand(new SeeKeysSubCommand());
        addSubCommand(new DeleteKeySubCommand());
        addSubCommand(new EditKeySubCommand());

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

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Player player = (Player) commandSender;
                        Bank bank = bankManager.getBank(player.getUniqueId());
                        if(bank != null){
                            commandSender.sendMessage("§aO seu saldo: " + bank.getCash());

                        }
                    }
                }.runTaskAsynchronously(plugin);

                return true;

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

            if(args.length > 1){
                showHelp(commandSender);
                return false;
            }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(subCommandTitle);
            if(!offlinePlayer.hasPlayedBefore()){
                commandSender.sendMessage("§cNao foi possivel encontrar esse jogador.");
            }else{

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        Bank bank = bankManager.getBank(offlinePlayer.getUniqueId());
                        if(bank != null){
                            commandSender.sendMessage("§aSaldo do jogador: " + bank.getCash());
                        }
                    }
                }.runTaskAsynchronously(plugin);

            }

            return true;

        }

        return true;
    }


}

