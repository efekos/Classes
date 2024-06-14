package dev.efekos.classes;

import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
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
import me.efekos.simpler.commands.node.impl.StringArgumentNode;
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

        try {

            CommandManager.registerCommandTree(this, new CommandTree("class", "Class command", "classes.use",
                    new ClassNameArgumentNode()
                            .addChild(
                                    new LabelNode("block")
                                            .addChild(
                                                    new LabelNode("material")
                                                            .addChild(new LabelNode("add").addChild(new MaterialNameArgumentNode().setExecutive(new AddMaterialBlockNode()).setPermission("classes.block.material.add")))
                                                            .addChild(new LabelNode("remove").addChild(new MaterialNameArgumentNode().setExecutive(new RemoveMaterialBlockNode()).setPermission("classes.block.material.remove")))
                                                            .addChild(new LabelNode("list").setExecutive(new MaterialListNode()).setPermission("classes.block.material.list"))
                                            )
                                            .addChild(
                                                    new LabelNode("enchantment")
                                                            .addChild(new LabelNode("add").addChild(new EnchantmentNameArgumentNode().setExecutive(new AddEnchantmentBlockNode()).setPermission("classes.block.enchantment.add")))
                                                            .addChild(new LabelNode("remove").addChild(new EnchantmentNameArgumentNode().setExecutive(new RemoveEnchantmentBlockNode()).setPermission("classes.block.enchantment.remove")))
                                                            .addChild(new LabelNode("list").setExecutive(new EnchantmentListNode()).setPermission("classes.block.enchantment.list"))
                                            )
                                            .addChild(
                                                    new LabelNode("potion")
                                                            .addChild(new LabelNode("add").addChild(new PotionNameArgumentNode().setExecutive(new AddPotionBlockNode()).setPermission("classes.block.potion.add")))
                                                            .addChild(new LabelNode("remove").addChild(new PotionNameArgumentNode().setExecutive(new RemovePotionBlockNode()).setPermission("classes.block.potion.remove")))
                                                            .addChild(new LabelNode("list").setExecutive(new PotionListNode()).setPermission("classes.block.potion.list"))
                                            )
                            )
                            .addChild(
                                    new LabelNode("modifier")
                                            .addChild(new LabelNode("add").addChild(new ModifierIDArgumentNode().addChild(new DoubleArgumentNode().setExecutive(new AddModifierNode()).setPermission("classes.modifier.add"))))
                                            .addChild(new LabelNode("remove").addChild(new ModifierIDArgumentNode().setExecutive(new RemoveModifierNode()).setPermission("classes.modifier.remove")))
                                            .addChild(new LabelNode("list").setExecutive(new ModifiersNode()).setPermission("classes.modifier.list"))
                            )
                            .addChild(
                                    new LabelNode("perk")
                                            .addChild(new LabelNode("add").addChild(new PerkIDArgumentNode().setExecutive(new AddPerkNode()).setPermission("classes.perk.add")))
                                            .addChild(new LabelNode("remove").addChild(new PerkIDArgumentNode().setExecutive(new RemovePerkNode()).setPermission("classes.perk.remove")))
                                            .addChild(new LabelNode("list").setExecutive(new PerksNode()).setPermission("classes.perk.list"))
                            )
                            .addChild(new LabelNode("delete").setExecutive(new DeleteNode()).setPermission("classes.delete"))
                            .addChild(new LabelNode("join").setExecutive(new JoinNode()).setPermission("classes.join"))
                            .addChild(new LabelNode("kit")
                                    .addChild(new LabelNode("update").setExecutive(new UpdateKitNode()).setPermission("classes.kit.update"))
                                    .addChild(new LabelNode("clear").setExecutive(new ClearKitNode()).setPermission("classes.kit.clear"))
                                    .addChild(new LabelNode("get").setExecutive(new GetKitNode()).setPermission("classes.kit.get"))
                            )
                            .addChild(new LabelNode("members").setExecutive(new MembersNode()).setPermission("classes.members"))
                            .addChild(new LabelNode("set")
                                    .addChild(new LabelNode("criteria").addChild(new LevelCriteriaIDArgumentNode().setExecutive(new SetCriteriaNode()).setPermission("classes.set.criteria")))
                                    .addChild(new LabelNode("description").addChild(new StringArgumentNode().setExecutive(new SetDescriptionNode()).setPermission("classes.set.description")))
                                    .addChild(new LabelNode("icon").setExecutive(new SetIconNode()).setPermission("classes.set.icon"))
                            )
                            .addChild(new LabelNode("info").setExecutive(new InfoNode()).setPermission("classes.info"))
                    ,
                    new LabelNode("create").addChild(new StringArgumentNode().setExecutive(new CreateNode()).setPermission("classes.create")),
                    new LabelNode("leave").setExecutive(new LeaveNode()).setPermission("classes.leave"),
                    new LabelNode("choose").setExecutive(context -> {
                        if (context.isSenderPlayer()) {
                            MenuManager.Open(((Player) context.sender()), ChooseClassMenu.class);
                        }
                    }).setPermission("classes.choose")
            ));

        } catch (Exception e) {
            e.printStackTrace();
            getLogger().info("Couldn't register commands, disabling");
            getServer().getPluginManager().disablePlugin(this);
            return;
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
    public static Class getClassByName(String name) {
        for (Class aClass : CLASSES.getAll()) {
            if (aClass.getName().equals(name)) return aClass;
        }
        return null;
    }
}
