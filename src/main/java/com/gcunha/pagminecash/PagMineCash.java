package com.gcunha.pagminecash;

import com.gcunha.pagminecash.bank.BankManager;
import com.gcunha.pagminecash.commands.CommandCash;
import com.gcunha.pagminecash.data.CashData;
import com.gcunha.pagminecash.data.CashDataMySql;
import com.gcunha.pagminecash.listener.PlayerExitListener;
import com.gcunha.pagminecash.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PagMineCash extends JavaPlugin {

    private static PagMineCash instance;
    private BankManager bankManager;
    private CommandCash commandCash;
    private CashData dataManager;

    @Override
    public void onEnable() {
        this.instance = this;

        setupData();

        this.bankManager = new BankManager();

        this.commandCash = new CommandCash();
        setupListeners();
    }

    @Override
    public void onDisable() {
        getDataManager().closePool();
    }

    public static PagMineCash getInstance() {
        return instance;
    }

    public BankManager getBankManager() {
        return bankManager;
    }

    public CashData getDataManager() {
        return dataManager;
    }

    private void setupData(){
        //TODO:Verificar se vamos utilizar MySQl ou SQLITE
        this.dataManager = new CashDataMySql();
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerExitListener(), this);
    }
}
