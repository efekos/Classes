package dev.efekos.classes.commands;

import dev.efekos.arn.annotation.Command;
import dev.efekos.arn.annotation.CommandArgument;
import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.annotation.block.BlockCommandBlock;
import dev.efekos.arn.annotation.block.BlockConsole;
import dev.efekos.arn.annotation.modifier.Greedy;
import dev.efekos.arn.annotation.modifier.Word;
import dev.efekos.arn.exception.ArnSyntaxException;
import dev.efekos.arn.exception.type.Dynamic2ArnExceptionType;
import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.ILevelCriteria;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.ModifierApplier;
import dev.efekos.classes.menu.ChooseClassMenu;
import dev.efekos.classes.menu.ClassInfoMenu;
import dev.efekos.classes.menu.ClassMembersMenu;
import dev.efekos.simple_ql.query.QueryBuilder;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Container
public class UsageCommands {

    public static final Dynamic2ArnExceptionType<ArnSyntaxException, String, String> GENERIC = new Dynamic2ArnExceptionType<>((o, o2) -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString(o, o2))));

    @Command(value = "class.leave", permission = "classes.use", description = "Leave your class")
    @BlockCommandBlock
    @BlockConsole
    public int leaveClass(Player player) throws ArnSyntaxException {
        if (!ClassManager.hasClass(player.getUniqueId()))
            throw GENERIC.create("commands.leave.not-class", "&cYou are not in a class.");
        dev.efekos.classes.data.Class clas = ClassManager.getClass(player.getUniqueId());

        for (ModifierApplier modifierApplier : clas.getModifiers()) {
            IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
            if (IModifier != null) IModifier.deapply(player);
        }
        for (String perk : clas.getPerks()) Main.PERK_REGISTRY.get(NamespacedKey.fromString(perk)).degrade(player);

        ClassManager.removeClass(player.getUniqueId());
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.leave.done", "&aSuccessfully left your class!")));

        if (Main.CONFIG.getBoolean("class-required", true)) MenuManager.Open(player, ChooseClassMenu.class);
        return 0;
    }

    @Command(value = "class.create", permission = "classes.create", description = "Create a class")
    @BlockCommandBlock
    public int createClass(CommandSender sender, @CommandArgument @Word String name, @CommandArgument ILevelCriteria criteria, @CommandArgument @Greedy String description) {
        if (Main.CLASSES.query(new QueryBuilder().sortAscending("name").getQuery()).result().stream().anyMatch(aClass -> aClass.getName().equals(name))) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.exists", "&cA class called &b%name% &calready exists.").replace("%name%", name)));
            return 1;
        }

        Main.CLASSES.insertRow(clas -> {
            clas.setName(name);
            clas.setLevelCriteria(criteria);
            clas.setDescription(description);
            clas.setIcon(new ItemStack(Material.IRON_SWORD,1));

            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.done", "&aSuccessfully created a class called &b%name%&a!").replace("%name%", name)));
        });
        return 0;
    }

    @Command(value = "class.create", permission = "classes.create", description = "Create a class")
    @BlockCommandBlock
    public int createClass(CommandSender sender, @CommandArgument @Word String name, @CommandArgument ILevelCriteria criteria) {
        return createClass(sender, name, criteria, "Another class.");
    }

    @Command(value = "class.delete", permission = "classes.delete", description = "Delete a class")
    @BlockCommandBlock
    public int deleteClass(CommandSender sender, @CommandArgument("class") Class clas) {
        ClassManager.getDatas().forEach((uuid, playerData) -> {
            if (playerData.getCurrentClass() == null) return;
            if (playerData.getCurrentClass().equals(clas.getUniqueId())) {
                Player p = Bukkit.getPlayer(uuid);
                if (p == null) return;
                clas.getPerks().forEach(perkApplier -> Main.PERK_REGISTRY.get(NamespacedKey.fromString(perkApplier)).degrade(p));
                clas.getModifiers().forEach(modifierApplier -> Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId()).deapply(p));
                playerData.setCurrentClass(null);
                playerData.getClassLevels().remove(clas.getUniqueId());
                p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.notification", "&eThe &b%class% &eclass you're in just got deleted by a server admin.").replace("%class%", clas.getName())));
            }
        });

        clas.delete();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.done", "&aSuccessfully deleted the class &b%name%&a! Keep in mind that some users of this class might still have some of its modifiers activated until respawn.").replace("%name%", clas.getName())));
        return 0;
    }

    @Command(value = "class.a:0:info", permission = "classes.use", description = "Information about a class.")
    @BlockCommandBlock
    @BlockConsole
    public int classInfo(Player player, @CommandArgument("class") Class clas) {
        MenuData data = MenuManager.getMenuData(player);
        data.set("class", clas.getUniqueId());
        MenuManager.updateMenuData(player, data);
        MenuManager.Open(player, ClassInfoMenu.class);
        return 0;
    }

    @Command(value = "classinfo", permission = "classes.use", description = "Information about a class.")
    @BlockCommandBlock
    @BlockConsole
    public int classInfo(Player player) {
        Class clas = ClassManager.getClass(player.getUniqueId());
        if (clas == null) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class-i", "&cYou are not in a class.")));
            return 1;
        }
        return classInfo(player, clas);
    }

    @Command(value = "class.info", permission = "classes.use", description = "Information about a class.")
    @BlockCommandBlock
    @BlockConsole
    public int classInfo2(Player player) {
        return classInfo(player);
    }

    @Command(value = "class.join", permission = "classes.use", description = "Join a class.")
    @BlockCommandBlock
    public int joinClass(Player player, @CommandArgument("class") Class clas) {

        if (ClassManager.hasClass(player.getUniqueId())) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.join.another", "&cYou are already in another class. Consider leaving it with &b/class leave &cbefore joining a new one.")));
            return 1;
        }

        ClassManager.setClass(player.getUniqueId(), clas);

        for (ModifierApplier modifierApplier : clas.getModifiers()) {
            IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
            if (IModifier != null)
                IModifier.apply(player, ClassManager.getLevel(player.getUniqueId()), modifierApplier.getValue());
        }
        for (String perk : clas.getPerks())
            Main.PERK_REGISTRY.get(NamespacedKey.fromString(perk)).grant(player, ClassManager.getLevel(player.getUniqueId()));

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.join.done", "&aSuccessfully joined &b%class% &aclass! Check out your pros and cons by typing &b/classinfo&a.").replace("%class%", clas.getName())));
        return 0;
    }

    @Command(value = "class.a:0:members", permission = "classes.members", description = "Members of a class.")
    @BlockCommandBlock
    @BlockConsole
    public int classMembers(Player player, @CommandArgument("class") Class clas) {
        MenuData data = MenuManager.getMenuData(player);
        data.set("class", clas.getUniqueId());
        MenuManager.updateMenuData(player, data);
        MenuManager.Open(player, ClassMembersMenu.class);
        return 0;
    }

    @Command(value = "class.choose", permission = "classes.use", description = "Choose a class.")
    @BlockCommandBlock
    @BlockConsole
    public int choose(Player player) {
        MenuManager.Open(player, ChooseClassMenu.class);
        return 0;
    }

}
