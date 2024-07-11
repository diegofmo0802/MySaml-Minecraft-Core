package com.mysaml.mc.core;

import com.mysaml.utilities;
import com.mysaml.mc.base.Base;
import com.mysaml.mc.core.command.*;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    Loader loader;
    public static void main(String[] args) {
        utilities.print(new String[] {
            "╔═══════════════════════════════════════════════════════════╗",
            "║══> Este archivo no se puede ejecutar por si mismo         ║",
            "║══> Por favor inserte en la carpeta Plugins de su servidor ║",
            "║══> En el futuro introduciremos la configuración aquí      ║",
            "║══> Para mas info: dci.mc.mysaml.com                       ║",
            "║                                                           ║",
            "║══> Att: diegofmo0802 <diegofmo0802@mysaml.com>            ║",
            "╚═══════════════════════════════════════════════════════════╝"
        });
    }
    public void onLoad() {}
    @Override
    public void onEnable() {
        getLogger().info(Base.Color("&dLoading commands"));
        loader = new Loader("./plugins/MySaml/addons", this, getClassLoader());
        utilities.print("plugins loaded: " + loader.loadPlugins().size());
        getLogger().info(Base.Color("&d╔═════════════════════╗"));
        getLogger().info(Base.Color("&d║ &bMySaml.Core &aEnabled &d║"));
        getLogger().info(Base.Color("&d╚═════════════════════╝"));
        new MainCommand(this, loader, "MySaml");
    }
    @Override
    public void onDisable() {
        getLogger().info(Base.Color("&dUnloading commands"));
        loader.unloadPlugins();
        getLogger().info(Base.Color("&d╔══════════════════════╗"));
        getLogger().info(Base.Color("&d║ &bMySaml.Core &4Disabled &d║"));
        getLogger().info(Base.Color("&d╚══════════════════════╝"));
    }
}
