package com.mysaml.mc.base;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysaml.mc.core.Loader;

public abstract class CommandExec implements CommandExecutor, TabCompleter {
    public final String CommandName;
    public JavaPlugin MainClass;
    public Loader PluginLoader;
    public PluginCommand Command;
    public CommandExec(JavaPlugin MainClass, Loader loader, String CommandName) {
        this.CommandName = CommandName;
        this.MainClass = MainClass;
        this.PluginLoader = loader;
        this.Command = MainClass.getCommand(CommandName);
        this.Command.setExecutor(this);
        this.Command.setTabCompleter(this);
    }
}
