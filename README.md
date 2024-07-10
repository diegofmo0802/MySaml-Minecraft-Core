# MySaml-Minecraft-Core

Este proyecto consiste en una plugin loader basado en su propia api de creacion de plugins junto al api propia de spigot

## Descarga e instalación

### Opciones de descarga
- Desde el apartado [releases](https://github.com/diegofmo0802/MySaml-Minecraft-Core/releases) y descargar el archivo jar correspondiente
- Clonar la rama del repositorio y compilarlo por ti mismo

### Instalación
- Copia el archivo jar del plugin
- dirígete a la carpeta plugins de tu servidor
- pega en esta carpeta el archivo jar
- reinicia el servidor

Una vez hayas hecho este proceso en tu carpeta plugins habrá una nueva carpeta "MySaml"
dentro de esta carpeta encontraras otra carpeta llamada "addons"
en la carpeta addons deberás poner los archivos jar que necesiten MySaml-Minecraft-Core


### Creación de plugins utilizando MySaml-Minecraft-Core
Para esto primero debes crear un proyecto de **java with maven**
Tambien debes añadir las dependencias tanto de MySaml-Minecraft-Core como la de spigot

#### Repositorios
```xml
<repositories>
    <repository>
        <id>mysaml</id>
        <url>https://repo.mysaml.com/</url>
    </repository>
    <repository>
        <id>spigot-repo</id>
        <url>https://hub.spigotmc.org/nexus/content/repositories/snapshots/</url>
    </repository>
</repositories>
```
#### Dependencias
```xml
<dependencies>
    <dependency>
        <groupId>org.spigotmc</groupId>
        <artifactId>spigot-api</artifactId>
        <version>1.20.6-R0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
          <groupId>com.mysaml.mc</groupId>
          <artifactId>core</artifactId>
          <version>1.0.0</version>
        <scope>provided</scope>
      </dependency>
</dependencies>
```
### addon.yml
En la carpeta `src/main/resources/` se encuentra el archivo `addon.yml`
este archivo debe tener los siguientes atributos

|Atributo|Tipo  |descripción                                                        |
|:-------|:----:|:------------------------------------------------------------------|
|name    |String|El nombre del plugin, este debe ser único para no causar conflictos|
|version |String|La version de tu addon                                             |
|main    |String|La clase main del plugin (extiende de MySamlAddon)                 |

**addon.yml**
```yml
name: MySamlAddon
version: 1.0.0
main: com.example.MySamlAddon
```

### clase principal del plugin
Tu clase principal debe extender de MySamlAddon
Esta clase tiene como requisitos hacer override a la función y onEnable() y onDisable()

Main.Java
```java
    package com.example;

    import com.mysaml.mc.api.MySamlAddon;

    public class Main extends MySamlAddon {
        public Main instance;

        @Override
        public void onEnable() {
            instance = this;
        }

        @Override
        public void onDisable() {
        }
    }
```

**En proceso de redacción**