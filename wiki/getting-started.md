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
    <version>1.0.0-3</version>
</dependency>
````

## Gradle

1 - Put this repository to your `repositories` tag.
````gradle
maven { url 'https://jitpack.io' }
````

2 - Put this dependency to your `dependencies` tag. You can use the latest release of [ClassesAPI](https://github.com/efekos/ClassesAPI) as version.
````gradle
implementation 'com.github.efekos:ClassesAPI:1.0.0-3'
````

# Accessing to the services

Classes make its API work using server services. So you need to use `Server#getServicesManager` in your main plugin class to access a service. For example:

````java
package me.efekos.newplugin;

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
package me.efekos.newplugin;

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

> **NOTE** - If you see any error, there is a high chance that it's because the server loads your plugin before Classes, so there is no services when you try to get the services. You need to
> make your plugin depend on Classes in the `plugin.yml` file. Like this:
````yaml
name: Classes
main: dev.efekos.classes.Main
bla: bla
depend:
  - Classes # do this to make sure that your plugin loads after Classes as expected.
````

# Adding a criteria

Let's start with the easiest thing, level criteria! Level criterias are simply ways of getting levels. Almost every event about player calls a `CriteriaCheckEvent`, and handles
giving exp and level to player. To make your criteria work just like any other, you need to use an `ILevelCriteria`. For example:

````java
package me.efekos.newplugin.criteria;

import dev.efekos.classes.api.i.ILevelCriteria;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementCriteria implements ILevelCriteria {
    @Override
    public boolean shouldGiveExp(Event event) {
        return event instanceof PlayerMoveEvent; // if event is a PlayerMoveEvent, then player has filled our conditions by moving.
    }
}
````

Then, you need to register the criteria to `LevelCriteriaRegistry` in your main class.

````java
import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import org.bukkit.NamespacedKey;
import me.efekos.newplugin.criteria.MovementCriteria;

class Main extends JavaPlugin {
    //...
    public static LevelCriteriaRegistry CRITERIA_REGISTRY;

    @Override
    public void onEnable() {
        Logger logger = getServer().getLogger();
        //...
        if (!setupCriteriaRegistry()) logger.warning("Criteria service doesn't exist. Some features of NewPlugin won't work.");

        CRITERIA_REGISTRY.register(new NamespacedKey(this,"move"),new MovementCriteria());
    }

    //...

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

# Adding a Modifier

Registering a modifier works kinda the same way, so I'll just explain how `IModifier`s work.

````java
package me.efekos.newplugin.modifier;

import dev.efekos.classes.api.i.IModifier;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpBoostModifier implements IModifier {
    @Override
    public void apply(Player player, int level, double value) { // executed everytime player respawns and when joins to the class.
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,(int) (level*value*60*20),2,true,true,true));
    }

    @Override
    public void deapply(Player player) { // executed when player leaves the class.
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    @Override
    public String getDescription(int level, double value) { // executed with level of the player that will see this and the value that's given for this modifier in class. 
        return "Gives you jump boost for %a% minutes" // default chat color is yellow.
                .replace("%a%",ChatColor.AQUA+""+Math.floor(level*value)+ChatColor.YELLOW);
    }

    @Override
    public boolean isPositive(int level, double value) { // this is important to know about where to put this modifier in /class info menu.
        return Math.floor(level*value)>0;
    }
}

````

# Adding a Perk

Registering a perk is the same way too, all you have to do is take a look at `IPerk`.

