package com.mysaml.mc.core.command;

import com.mysaml.mc.base.CommandExec;
import com.mysaml.mc.core.Loader;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MainCommand extends CommandExec {
    public MainCommand(JavaPlugin MainClass, Loader loader, String CommandName) {
        super(MainClass, loader, CommandName);
    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("&bNo help message");
            return true;
        } else {
            String cmd = args[0];
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
            if (PluginLoader.commands.containsKey(cmd)) {
                PluginLoader.commands.get(cmd).getMainClass().getCommands().get(cmd).onCommand(sender, subArgs);
            } else sender.sendMessage("this command not exists");
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> Result = new ArrayList<String>();
        if (args.length == 1) {
            Result.addAll(PluginLoader.commands.keySet());
        } else if (args.length > 1) {
            String cmd = args[0];
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, args.length - 1);
            if (PluginLoader.commands.containsKey(cmd)) {
                Result = PluginLoader.commands.get(cmd).getMainClass().getCommands().get(cmd).onTab(sender, subArgs);
            }
        }
        return Result;
    }
}
