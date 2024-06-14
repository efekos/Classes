package dev.efekos.classes;

import dev.efekos.arn.Arn;
import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.events.*;
import dev.efekos.classes.registry.ClassesCriterias;
import dev.efekos.classes.registry.ClassesModifiers;
import dev.efekos.classes.registry.ClassesPerks;
import me.efekos.simpler.Metrics;
import me.efekos.simpler.config.ListDataManager;
import me.efekos.simpler.config.YamlConfig;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static YamlConfig LANG;
    public static YamlConfig CONFIG;
    public static ListDataManager<Class> CLASSES;

    public static ModifierRegistry MODIFIER_REGISTRY;
    public static PerkRegistry PERK_REGISTRY;
    public static LevelCriteriaRegistry CRITERIA_REGISTRY;

    private static Metrics metrics;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        CLASSES = new ListDataManager<>("\\ClassData.json", this);
        LANG = new YamlConfig("lang.yml", this);
        CONFIG = new YamlConfig("config.yml", this);
        LANG.setup();
        CONFIG.setup();
        metrics = new Metrics(this, 20226);
        MenuManager.setPlugin(this);
        CLASSES.load(Class[].class);
        ClassManager.load(this);
        getServer().getPluginManager().registerEvents(new BlockingEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new ModifierApplyingEvents(), this);
        getServer().getPluginManager().registerEvents(new HandlingEvents(), this);
        getServer().getPluginManager().registerEvents(new CriteriaCheckEventListeners(), this);

        ServicesManager manager = getServer().getServicesManager();

        manager.register(ModifierRegistry.class, new ModifierRegistry(), this, ServicePriority.Normal);
        manager.register(PerkRegistry.class, new PerkRegistry(), this, ServicePriority.Normal);
        manager.register(LevelCriteriaRegistry.class, new LevelCriteriaRegistry(), this, ServicePriority.Normal);

        MODIFIER_REGISTRY = manager.getRegistration(ModifierRegistry.class).getProvider();
        PERK_REGISTRY = manager.getRegistration(PerkRegistry.class).getProvider();
        CRITERIA_REGISTRY = manager.getRegistration(LevelCriteriaRegistry.class).getProvider();

        ClassesModifiers.KNOCKBACK_RESISTANCE.getClass(); // calling this will load the entire ClassesModifiers class so everything will be registered
        ClassesPerks.FAST_ARROW.getClass();
        ClassesCriterias.TAKE_DAMAGE.getClass();

        Arn.run(Main.class);

        getLogger().info("Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CLASSES.save();
        ClassManager.save(this);
    }

    @Nullable
    public static Class getClassByName(String name) {
        for (Class aClass : CLASSES.getAll()) {
            if (aClass.getName().equals(name)) return aClass;
        }
        return null;
    }
}
