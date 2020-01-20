package com.gcunha.pagminecash;

import com.gcunha.pagminecash.bank.BankManager;
import com.gcunha.pagminecash.commands.CommandCash;
import com.gcunha.pagminecash.data.CashData;
import com.gcunha.pagminecash.data.CashDataMySql;
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
        this.bankManager = new BankManager();
        this.commandCash = new CommandCash();

        setupData();
        setupListeners();

        getDataManager().getBank(UUID.fromString("fcb6d822-c7e2-4bfc-9e9b-14069260ab0d"));
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
    }
}
