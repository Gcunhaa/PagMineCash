package com.gcunha.pagminecash.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommand {

    private String title;
    private int argSize;
    private String permission;
    private String description;
    private String syntax;

    public SubCommand(String title, int argsSize, String permission, String description, String syntax) {
        this.title = title;
        this.argSize = argsSize;
        this.permission = permission;
        this.description = description;
        this.syntax = syntax;
    }

    public boolean execute(CommandSender commandSender, String[] args) throws Exception{


        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArgSize() {
        return argSize;
    }

    public void setArgSize(int argSize) {
        this.argSize = argSize;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }
}
