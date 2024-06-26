package com.mysaml.mc.api;

import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public abstract class MySamlAddon {
    public Map<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
    public Map<String, EventListener> events = new HashMap<String, EventListener>();
    static private Plugin Core;
    static private boolean isLoaded = false;
    static public URLClassLoader loader;
    // Add-On Sources
    static public Plugin getCore() { return Core; }
    static public boolean isLoaded() { return isLoaded; }
    // Add-On Events
    public void init(Plugin Core, URLClassLoader thiLoader) {
        MySamlAddon.Core = Core;
        MySamlAddon.loader = thiLoader;
        MySamlAddon.isLoaded = true;
    }
    public void enable() throws Exception {
        if (! isLoaded) throw new Exception("Estas intentando habilitar el plugin antes de iniciarlo");
        onEnabled();
    }
    public void disable() {
        onDisabled();
    }
    public abstract void onEnabled();
    public abstract void onDisabled();
    // Add-On Command manager
    public void addCommand(String commandName, CommandExecutor command) {
        commands.put(commandName, command);
    }
    public void delCommand(String commandName) {
        commands.remove(commandName);
    }
    public Map<String, CommandExecutor> getCommands() { return commands; }
    public boolean executeCommand(String commandName, CommandSender sender, String[] args) {
        if (! commands.containsKey(commandName)) return false;
        commands.get(commandName).onCommand(sender, args);
        return true;
    }
    // Add-on EventManager
    public void addEventListener(String eventName, EventListener listener) {
        events.put(eventName, listener);
    }
    public void delEventListener(String eventName) {
        events.remove(eventName);
    }
    public Map<String, EventListener> getEvents() { return events; }
}
