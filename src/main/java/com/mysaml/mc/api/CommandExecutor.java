package com.mysaml.mc.api;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface CommandExecutor {
    public void onCommand(CommandSender sender, String[] args);
    default public List<String> onTab(CommandSender sender, String[] args) {
        return null;
    }
}
