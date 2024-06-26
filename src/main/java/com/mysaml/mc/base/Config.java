package com.mysaml.mc.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends YamlConfiguration {
    private String fileName;
    private String filePath;
    private File configFile;
    private ClassLoader loader;
    public Config(String file) {
        Init(file, this.getClass().getClassLoader());
    }
    public Config(String file, ClassLoader loader) {
        Init(file, loader);
    }
    private void Init(String fileName, ClassLoader loader) {
        this.fileName = fileName;
        this.filePath = "plugins/MySaml/" + fileName;
        this.configFile = new File(filePath);
        this.loader = loader;
        reload();
    }
    public void reload() {
        try {
            System.out.println("[MySaml] Cargando archivo: "+ filePath);
            load(configFile);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("[MySaml] Error al cargar " + configFile.getName() + e.getMessage());
        } catch (InvalidConfigurationException e) {
            System.out.println("[MySaml] Error al cargar " + configFile.getName() + e.getMessage());
        }
        try {
            InputStream inputStream = loader.getResourceAsStream(fileName);
            if (inputStream != null) {
                Reader dataReader = new InputStreamReader(inputStream);
                setDefaults(YamlConfiguration.loadConfiguration(dataReader));
            } else {
                System.out.println("[MySaml] No hay atributos predeterminados para: " + fileName);
            }
        } catch(Exception/* NullPointerException */ e) {
            System.out.println("[MySaml] Error al obtener valores predeterminados: " + e.getMessage());
        }
        if (! configFile.exists()) {
            try {
                if (configFile.getParentFile().exists()) configFile.getParentFile().mkdirs();
                System.out.println("[MySaml]: No existe, creando... &6" + filePath);
                options().copyDefaults(true);
                save(configFile);
            } catch (IOException err) {
                System.out.println("[MySaml] Error al guardar " + configFile.getName() + err.getMessage());
            }
        }
    }
    public void save() throws IOException {
        save(configFile);
    }
}
