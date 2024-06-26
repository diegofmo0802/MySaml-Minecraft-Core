package com.mysaml.mc.core;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import com.mysaml.utilities;
import com.mysaml.mc.api.MySamlAddon;
import com.mysaml.mc.base.AddonClassLoader;

public class Addon {
    ClassLoader loaderParent;
    public String
        name = "unloaded",
        version = "unloaded",
        main = "unloaded";
    protected boolean
        loaded = false,
        valid = true;
    protected File addonFile;
    protected MySamlAddon mainClass = null;
    public Addon(File addon, ClassLoader parent) {
        this.loaderParent = parent;
        addonFile = addon;
        try {
            JarFile jarFile = new JarFile(addon);
            if (isValidPlugin(jarFile)) {
                Map<String, Object> info = getInfo(jarFile);
                name = (String) info.get("name");
                version = (String) info.get("version");
                main = (String) info.get("main");
            } else valid = false;
        } catch (Exception e) {
            utilities.print(e.getMessage());
            valid = false;
        }                        
    }
    public boolean isValid() { return valid; }
    public MySamlAddon getMainClass() {
        if (! loaded) return null;
        return mainClass;
    }
    public MySamlAddon load(Plugin Core) throws Exception {
        return load(Core, true);
    }
    public MySamlAddon load(Plugin Core, boolean autoEnable) throws Exception {
        URL addonUrl = addonFile.toURI().toURL();
        URLClassLoader loader = new AddonClassLoader(new URL[] { addonUrl }, loaderParent);
        Class<?> addonClass = Class.forName(main, false, loader);
        if (!MySamlAddon.class.isAssignableFrom(addonClass))
        throw new Exception("the main class no extends from MySamlAddon >> \"" + addonFile.getName() + "\"");
        mainClass = (MySamlAddon) addonClass.getDeclaredConstructor().newInstance();
        mainClass.init(Core, loader);
        if (autoEnable) enable();
        loaded = true;
        return mainClass;
    }
    public void enable() {
        try { mainClass.enable(); }
        catch(Exception e) {
            utilities.print("Error al habilitar \"" + name + "\"" + e.getMessage());
        }
    }
    public void disable() { mainClass.disable(); }
    public static Map<String, Object> getInfo(File addon) {      
        try {
            if (! isJarFile(addon)) throw new Exception("no is a jar file >> \"" + addon.getName() + "\"");
            JarFile jarFile = new JarFile(addon);
            Map<String, Object> info = getInfo(jarFile);
            jarFile.close();
            return info;
        } catch (Exception e) {
            utilities.print(e.getMessage());
            return null;
        }  
    }
    public static Map<String, Object> getInfo(JarFile addon) {
        try {
            JarEntry infoEntry = addon.getJarEntry("plugin.yml");
            InputStream infoStream = addon.getInputStream(infoEntry);
            if (infoStream == null) throw new Exception("PF-NE");
            Yaml yamlReader = new Yaml();
            Map<String, Object> info = yamlReader.load(infoStream);
            return info;
        } catch (Exception e) {
            if (e.getMessage() != "PF-NE")
            utilities.print(e.getMessage());
            return null;
        }
    }
    protected static boolean isValidPlugin(File addon) {
        try {
            if (! isJarFile(addon)) throw new Exception("no is a jar file >> \"" + addon.getName() + "\"");
            JarFile jarFile = new JarFile(addon);
            boolean isValid = isValidPlugin(jarFile);
            jarFile.close();
            return isValid;
        } catch(Exception e) {
            utilities.print(e.getMessage());
            return false;
        }
    }
    protected static boolean isValidPlugin(JarFile addon) {
        boolean isValid = true;
        try {
            Map<String, Object> addonInfo = getInfo(addon);

            if (addonInfo == null) {
                addon.close();
                throw new Exception("plugin.yml not exists >> \"" + addon.getName() + "\"");
            }
            if (!addonInfo.containsKey("name"))
            throw new Exception("[Error]: plugin.yml no contains \"name\" >> \"" + addon.getName() + "\"");
            if (!addonInfo.containsKey("version"))
            throw new Exception("[Error]: plugin.yml no contains \"version\" >> \"" + addon.getName() + "\"");
            if (!addonInfo.containsKey("main")) 
            throw new Exception("plugin.yml no contains \"main\" >> \"" + addon.getName() + "\"");
        } catch (Exception e) {
            isValid = false;
            utilities.print(e.getMessage());
        }
        return isValid;
    }
    public static boolean isJarFile(File jarFile) {
        return (
            jarFile.exists() &&
            jarFile.getName().toLowerCase().endsWith(".jar")
        );
    }
}
