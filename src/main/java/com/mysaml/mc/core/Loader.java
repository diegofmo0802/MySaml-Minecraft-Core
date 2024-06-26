package com.mysaml.mc.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.mysaml.utilities;
import com.mysaml.mc.api.MySamlAddon;

public class Loader {
    public String rootDir;
    public List<Addon> addons = new ArrayList<Addon>();
    public Map<String, Addon> commands = new HashMap<String, Addon>();
    public ClassLoader loaderParent;
    public Plugin mainPlugin;
    public Loader(String rootDir, Plugin mainPlugin, ClassLoader loader) {
        this.rootDir = rootDir;
        this.loaderParent = loader;
        this.mainPlugin = mainPlugin;
    }
    public List<Addon> loadPlugins() {
        File AddonFolder = new File(rootDir);
        if (! AddonFolder.exists()) AddonFolder.mkdirs();
        if (! AddonFolder.isDirectory()) {
            System.out.println("\"" + rootDir + "\" not is a directory");
            return addons;
        }
        for (File addonFile : AddonFolder.listFiles()) {
            try {
                Addon addon = new Addon(addonFile, loaderParent);
                if (! addon.isValid()) continue;
                utilities.print(new String[] {
                    "╔═══>> MySaml.core <<══════════════",
                    "╠>> Addon name: " + addon.name,
                    "╠>> Addon version: " + addon.version,
                    "╠>> Addon mainClass: " + addon.main,
                    "╠══════════════════════════════════"
                });
                addon.load(mainPlugin);
                MySamlAddon addonClass = addon.getMainClass();
                addonClass.commands.keySet().forEach((commandName) -> {
                    utilities.print("╠═╦═>> command: " + commandName);
                    if (this.commands.containsKey(commandName)) {
                        utilities.print(
                            "║ ╚═<< the command name is used for: " +
                            this.commands.get(commandName).name
                        );
                    } else {
                        utilities.print("║ ╚═<< command loaded");
                        this.commands.put(commandName, addon);
                    }
                });
                addonClass.events.forEach((eventName, listener) -> {
                    utilities.print(new String[] {
                        "╠═╦═>> classListener: " + eventName,
                        "║ ╚═<< listener loaded"
                    });
                    mainPlugin.getServer().getPluginManager().registerEvents(listener, mainPlugin);
                });
                addons.add(addon);
            } catch (Exception e) {
                utilities.print("Failed to load plugin \"" + addonFile.getName() + "\"");
                utilities.print("Error info: " + e.toString());
            }
        }
        return addons;
    }
    public void unloadPlugins() {
        addons.forEach((plugin) -> {
            plugin.disable();
        });
    }
}
