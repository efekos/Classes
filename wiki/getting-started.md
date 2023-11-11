# Installation

## Maven

1 - Put this repository to your `repositories` tag.
````xml
<repository>
    <id>jitpack</id>
    <url>https://jitpack.io/</url>
</repository>
````

2 - Put this dependency to your `dependencies` tag. You can use the latest release of [ClassesAPI](https://github.com/efekos/ClassesAPI) as version.
````xml
<dependency>
    <groupId>com.github.efekos</groupId>
    <artifactId>ClassesAPI</artifactId>
    <version>1.0.0</version>
</dependency>
````

## Gradle

1 - Put this repository to your `repositories` tag.
````gradle
maven { url 'https://jitpack.io' }
````

2 - Put this dependency to your `dependencies` tag. You can use the latest release of [ClassesAPI](https://github.com/efekos/ClassesAPI) as version.
````gradle
implementation 'com.github.efekos:ClassesAPI:1.0.0'
````

# Accessing to the services

Classes make its API work using server services. So you need to use `Server#getServerServicesManager` in your main plugin class to access a service. For example:

````java
package me.efekos.newplugin.Main;

import dev.efekos.classes.api.registry.ModifierRegistry;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

class Main extends JavaPlugin {
    public static ModifierRegistry MODIFIER_REGISTRY;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<ModifierRegistry> serviceProvider = getServer().getServicesManager().getRegistration(ModifierRegistry.class);
        MODIFIER_REGISTRY = serviceProvider.getProvider();
    }
}
````

You might be familiar with this from Vault, which is another plugin that uses server service manager as well. If you are, you can just use the same logic with different
classes. If you're not, I recommend something like this for getting all the services.

````java
package me.efekos.newplugin.Main;

import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

class Main extends JavaPlugin {
    public static ModifierRegistry MODIFIER_REGISTRY;
    public static PerkRegistry PERK_REGISTRY;
    public static LevelCriteriaRegistry CRITERIA_REGISTRY;

    @Override
    public void onEnable() {
        Logger logger = getServer().getLogger();
        if (!setupModifierRegistry()) logger.warning("Modifier service doesn't exist. Some features of NewPlugin won't work."); // you can do whatever you want instead of this warning.
        if (!setupPerkRegistry()) logger.warning("Perk service doesn't exist. Some features of NewPlugin won't work.");
        if (!setupCriteriaRegistry()) logger.warning("Criteria service doesn't exist. Some features of NewPlugin won't work.");
    }

    public boolean setupModifierRegistry() { // checks for service and plugin existence and returns true if service exists.
        RegisteredServiceProvider<ModifierRegistry> serviceProvider = getServer().getServicesManager().getRegistration(ModifierRegistry.class);
        if (serviceProvider != null && serviceProvider.getPlugin().getName().equals("Classes")) {
            MODIFIER_REGISTRY = serviceProvider.getProvider();
            return true;
        } else {
            return false;
        }
    }

    public boolean setupPerkRegistry() {
        RegisteredServiceProvider<PerkRegistry> serviceProvider = getServer().getServicesManager().getRegistration(PerkRegistry.class);
        if (serviceProvider != null && serviceProvider.getPlugin().getName().equals("Classes")) {
            PERK_REGISTRY = serviceProvider.getProvider();
            return true;
        } else {
            return false;
        }
    }

    public boolean setupCriteriaRegistry() {
        RegisteredServiceProvider<LevelCriteriaRegistry> serviceProvider = getServer().getServicesManager().getRegistration(LevelCriteriaRegistry.class);
        if (serviceProvider != null && serviceProvider.getPlugin().getName().equals("Classes")) {
            CRITERIA_REGISTRY = serviceProvider.getProvider();
            return true;
        } else {
            return false;
        }
    }
}
````