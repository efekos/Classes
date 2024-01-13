package dev.efekos.classes;

import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import dev.efekos.classes.commands.arguments.ClassNameArgumentNode;
import dev.efekos.classes.commands.clazz.*;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.events.*;
import dev.efekos.classes.menu.ChooseClassMenu;
import dev.efekos.classes.registry.ClassesCriterias;
import dev.efekos.classes.registry.ClassesModifiers;
import dev.efekos.classes.registry.ClassesPerks;
import me.efekos.simpler.Metrics;
import me.efekos.simpler.commands.CommandManager;
import me.efekos.simpler.commands.CommandTree;
import me.efekos.simpler.commands.node.impl.LabelNode;
import me.efekos.simpler.config.ListDataManager;
import me.efekos.simpler.config.YamlConfig;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.entity.Player;
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
        CLASSES = new ListDataManager<>("\\ClassData.json",this);
        LANG = new YamlConfig("lang.yml",this);
        CONFIG = new YamlConfig("config.yml",this);
        LANG.setup();
        CONFIG.setup();
        metrics = new Metrics(this,20226);
        MenuManager.setPlugin(this);
        CLASSES.load(Class[].class);
        ClassManager.load(this);
        getServer().getPluginManager().registerEvents(new BlockingEvents(),this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(),this);
        getServer().getPluginManager().registerEvents(new ModifierApplyingEvents(),this);
        getServer().getPluginManager().registerEvents(new HandlingEvents(),this);
        getServer().getPluginManager().registerEvents(new CriteriaCheckEventListeners(),this);

        ServicesManager manager = getServer().getServicesManager();

        manager.register(ModifierRegistry.class,new ModifierRegistry(),this, ServicePriority.Normal);
        manager.register(PerkRegistry.class,new PerkRegistry(),this,ServicePriority.Normal);
        manager.register(LevelCriteriaRegistry.class,new LevelCriteriaRegistry(),this,ServicePriority.Normal);

        MODIFIER_REGISTRY = manager.getRegistration(ModifierRegistry.class).getProvider();
        PERK_REGISTRY = manager.getRegistration(PerkRegistry.class).getProvider();
        CRITERIA_REGISTRY = manager.getRegistration(LevelCriteriaRegistry.class).getProvider();

        ClassesModifiers.KNOCKBACK_RESISTANCE.getClass(); // calling this will load the entire ClassesModifiers class so everything will be registered
        ClassesPerks.FAST_ARROW.getClass();
        ClassesCriterias.TAKE_DAMAGE.getClass();

        try {

            CommandManager.registerCommandTree(this,new CommandTree("clas","Class command","classes.use",
                    new ClassNameArgumentNode()
                            .addChild(
                                    new LabelNode("block")
                                            .addChild(
                                                    new LabelNode("material")
                                                            .addChild(new LabelNode("add").addChild(new))
                                                            .addChild(new LabelNode("remove"))
                                                            .addChild(new LabelNode("list"))
                                            )
                                            .addChild(
                                                    new LabelNode("enchantment")
                                                            .addChild(new LabelNode("add"))
                                                            .addChild(new LabelNode("remove"))
                                                            .addChild(new LabelNode("list"))
                                            )
                                            .addChild(
                                                    new LabelNode("potion")
                                                            .addChild(new LabelNode("add"))
                                                            .addChild(new LabelNode("remove"))
                                                            .addChild(new LabelNode("list"))
                                            )
                            )
                            .addChild(
                                    new LabelNode("modifier")
                                            .addChild(new LabelNode("add"))
                                            .addChild(new LabelNode("remove"))
                                            .addChild(new LabelNode("list"))
                            )
                            .addChild(
                                    new LabelNode("perk")
                                            .addChild(new LabelNode("add"))
                                            .addChild(new LabelNode("remove"))
                                            .addChild(new LabelNode("list"))
                            )
                            .addChild(new LabelNode("delete"))
                            .addChild(new LabelNode("join"))
                            .addChild(new LabelNode("kit")
                                    .addChild(new LabelNode("update"))
                                    .addChild(new LabelNode("clear"))
                                    .addChild(new LabelNode("get"))
                            )
                            .addChild(new LabelNode("members").setExecutive(new MembersNode()))
                            .addChild(new LabelNode("set")
                                            .addChild(new LabelNode("criteria").setExecutive(new SetCriteriaNode()))
                                            .addChild(new LabelNode("description").setExecutive(new SetDescriptionNode()))
                                            .addChild(new LabelNode("icon").setExecutive(new SetIconNode()))
                                    )
                            .addChild(new LabelNode("info").setExecutive(new InfoNode()))
                    ,
                    new LabelNode("create"),
                    new LabelNode("leave"),
                    new LabelNode("choose").setExecutive(context -> {
                        if(context.isSenderPlayer()){
                            MenuManager.Open(((Player) context.sender()), ChooseClassMenu.class);
                        }
                    })
            ));

        } catch (Exception e){
            e.printStackTrace();
        }

        getLogger().info("Started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CLASSES.save();
        ClassManager.save(this);
    }

    @Nullable
    public static Class getClassByName(String name){
        for (Class aClass : CLASSES.getAll()) {
            if(aClass.getName().equals(name))return aClass;
        }
        return null;
    }
}
