package com.gcunha.pagminecash;

import com.gcunha.pagminecash.bank.BankManager;
import com.gcunha.pagminecash.commands.CommandCash;
import org.bukkit.plugin.java.JavaPlugin;

public class PagMineCash extends JavaPlugin {

    private static PagMineCash instance;
    private BankManager bankManager;
    private CommandCash commandCash;

    @Override
    public void onEnable() {
        this.instance = this;
        this.bankManager = new BankManager();
        this.commandCash = new CommandCash();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static PagMineCash getInstance() {
        return instance;
    }

    public BankManager getBankManager() {
        return bankManager;
    }
}
