package dev.efekos.classes.commands;

import dev.efekos.arn.annotation.Command;
import dev.efekos.arn.annotation.CommandArgument;
import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.annotation.block.BlockCommandBlock;
import dev.efekos.arn.annotation.modifier.Item;
import dev.efekos.arn.annotation.modifier.NumberLimitations;
import dev.efekos.arn.exception.ArnSyntaxException;
import dev.efekos.arn.exception.type.Dynamic2ArnExceptionType;
import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ModifierApplier;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

@Container
public class ARLCommands {

    // TODO exceptions

    public static final Dynamic2ArnExceptionType<ArnSyntaxException, String, String> GENERIC = new Dynamic2ArnExceptionType<>((o, o2) -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString(o, o2))));

    @Command(value = "class.a:0:block.a:0:enchantment.a:0:add", permission = "classes.block.enchantment.add", description = "Block an enchantment.")
    @BlockCommandBlock
    public int addEnchantment(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument Enchantment enchantment) throws ArnSyntaxException {

        List<Enchantment> list = clas.getBlockedEnchantments();
        if (list.contains(enchantment))
            throw GENERIC.create("commands.add-enchantment-block.already", "&cThat enchantment is already blocked for that class.");
        list.add(enchantment);
        clas.setBlockedEnchantments(list);
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.done", "&aSuccessfully blocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%", enchantment.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:enchantment.a:0:remove", permission = "classes.block.enchantment.remove", description = "Unblock an enchantment.")
    @BlockCommandBlock
    public int removeEnchantment(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument Enchantment enchantment) throws ArnSyntaxException {

        List<Enchantment> list = clas.getBlockedEnchantments();
        if (!list.contains(enchantment))
            throw GENERIC.create("commands.remove-enchantment-block.not-added", "&cThat enchantment is not blocked for that class.");
        list.remove(enchantment);
        clas.setBlockedEnchantments(list);
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-enchantment-block.done", "&aSuccessfully unblocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%", enchantment.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:enchantment.a:0:list", permission = "classes.block.enchantment.list", description = "List of blocked enchantments.")
    @BlockCommandBlock
    public int listEnchants(CommandSender sender, @CommandArgument("class") Class clas) {
        List<String> list = clas.getBlockedEnchantments().stream().map(enchantment -> enchantment.getKey().getNamespace() + ":" + enchantment.getKey().getKey()).toList();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.title-enchantment", "&eBlocked enchantments for class %class%:").replaceAll("%class%", clas.getName())));

        for (String string : list)
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.regex", "&5- &d%item%").replaceAll("%item%", string)));

        return 0;
    }

    @Command(value = "class.a:0:block.a:0:material.a:0:add", permission = "classes.block.material.add", description = "Block a material")
    @BlockCommandBlock
    public int addMaterial(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument @Item Material material) throws ArnSyntaxException {

        List<Material> list = clas.getBlockedMaterials();
        if (list.contains(material))
            throw GENERIC.create("commands.add-material-block.already", "&cThat material is already blocked for that class.");
        list.add(material);
        clas.setBlockedMaterials(list);
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-material-block.done", "&aSuccessfully blocked &b%material% &afor &b%class%&a!")
                .replace("%material%", material.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:material.a:0:list", permission = "classes.block.material.list", description = "List of blocked materials.")
    @BlockCommandBlock
    public int listMaterials(CommandSender sender, @CommandArgument("class") Class clas) {
        List<String> list = clas.getBlockedMaterials().stream().map(enchantment -> enchantment.getKey().getNamespace() + ":" + enchantment.getKey().getKey()).toList();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.title-material", "&eBlocked materials for class %class%:").replaceAll("%class%", clas.getName())));

        for (String string : list) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.regex", "&5- &d%item%").replaceAll("%item%", string)));
        }
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:material.a:0:remove", permission = "classes.block.material.remove", description = "Unblock a material.")
    @BlockCommandBlock
    public int removeMaterial(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument @Item Material material) throws ArnSyntaxException {
        List<Material> list = clas.getBlockedMaterials();
        if (!list.contains(material))
            throw GENERIC.create("commands.remove-material-block.not-added", "&cThat material is not blocked for that class.");
        list.remove(material);
        clas.setBlockedMaterials(list);
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-material-block.done", "&aSuccessfully unblocked &b%material% &afor &b%class%&a!")
                .replace("%material%", material.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:modifier.a:0:add", permission = "classes.modifier.add", description = "Add a modifier.")
    @BlockCommandBlock
    public int addModifier(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument IModifier modifier, @CommandArgument @NumberLimitations(max = 10, min = -10) double value) throws ArnSyntaxException {

        NamespacedKey key = Main.MODIFIER_REGISTRY.idOf(modifier);
        if (clas.getModifiers().stream().anyMatch(modifierApplier -> modifierApplier.getModifierId().equals(key)))
            throw GENERIC.create("commands.add-modifier.already", "&cThat modifier is already added for that class. Consider re-adding it using both &b/class removemodifier &cand &b/class addmodifier");

        clas.addModifier(new ModifierApplier(key, value));
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.done", "&aSuccessfully added &b%modifier% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
                .replace("%modifier%", key.toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:modifier.a:0:list", permission = "classes.modifier.list", description = "List of modifiers.")
    @BlockCommandBlock
    public int listModifiers(CommandSender sender, @CommandArgument("class") Class clas) {
        List<String> modifierStrings = new ArrayList<>();
        for (ModifierApplier modifierApplier : clas.getModifiers()) {
            IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
            if (IModifier == null) continue;

            modifierStrings.add(ChatColor.AQUA + modifierApplier.getModifierId().toString() + " &6 - &e" + IModifier.getDescription(1, modifierApplier.getValue()));
        }

        for (String s : modifierStrings) sender.sendMessage(TranslateManager.translateColors(s));
        return 0;
    }

    @Command(value = "class.a:0:modifier.a:0:remove", permission = "classes.modifier.remove", description = "Remove a modifier.")
    @BlockCommandBlock
    public int removeModifier(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument IModifier modifier) {
        NamespacedKey key = Main.MODIFIER_REGISTRY.idOf(modifier);

        if (clas.getModifiers().stream().noneMatch(modifierApplier -> modifierApplier.getModifierId().equals(key))) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.not-added", "&cThat modifier is not in that class.")));
            return 1;
        }
        clas.setModifiers(clas.getModifiers().stream().filter(modifierApplier -> !modifierApplier.getModifierId().equals(key)).toList());
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.done", "&aSuccessfully removed &b%modifier% &from &b%class%&a!")
                .replace("%modifier%", key.toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:perk.a:0:add", permission = "classes.perk.add", description = "Add a perk")
    @BlockCommandBlock
    public int addPerk(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument IPerk perk) throws ArnSyntaxException {
        NamespacedKey key = Main.PERK_REGISTRY.idOf(perk);

        if (clas.getPerks().stream().anyMatch(modifierApplier -> modifierApplier.equals(perk.toString())))
            throw GENERIC.create("commands.add-perk.already", "&cThat perk is already added for that class. Consider re-adding it using both &b/class removeperk &cand &b/class addperk");
        clas.addPerk(key.toString());

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.done", "&aSuccessfully added &b%perk% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
                .replace("%perk%", key.toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:perk.a:0:list", permission = "classes.perk.list", description = "List of perks")
    @BlockCommandBlock
    public int listPerks(CommandSender sender, @CommandArgument("class") Class clas) {
        List<String> perkStrings = new ArrayList<>();
        for (String applier : clas.getPerks()) {
            IPerk perk = Main.PERK_REGISTRY.get(NamespacedKey.fromString(applier));
            if (perk == null) continue;
            perkStrings.add(ChatColor.AQUA + applier + "&6 - &e" + perk.getDescription(1));
        }

        for (String s : perkStrings) sender.sendMessage(TranslateManager.translateColors(s));
        return 0;
    }

    @Command(value = "class.a:0:perk.a:0:remove", permission = "classes.perk.remove", description = "Remove a perk")
    @BlockCommandBlock
    public int removePerk(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument IPerk perk) throws ArnSyntaxException {
        NamespacedKey key = Main.PERK_REGISTRY.idOf(perk);

        if (clas.getPerks().stream().noneMatch(modifierApplier -> modifierApplier.equals(key.toString())))
            throw GENERIC.create("commands.remove-perk.not-added", "&cThat perk is not in that class.");
        clas.setPerks(clas.getPerks().stream().filter(modifierApplier -> !modifierApplier.equals(key.toString())).toList());
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.done", "&aSuccessfully removed &b%perk% &from &b%class%&a!")
                .replace("%perk%", key.toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:potion.a:0:add", permission = "classes.block.potion.add", description = "Block a potion.")
    @BlockCommandBlock
    public int addPotionBlock(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument PotionEffectType potion) throws ArnSyntaxException {
        List<PotionEffectType> list = clas.getBlockedPotions();
        if (list.contains(potion))
            throw GENERIC.create("commands.add-potion-block.already", "&cThat potion is already blocked for that class.");
        list.add(potion);
        clas.setBlockedPotions(list);
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-potion-block.done", "&aSuccessfully blocked &b%potion% &afor &b%class%&a!")
                .replace("%potion%", potion.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:potion.a:0:list", permission = "classes.block.potion.list", description = "List of potions")
    @BlockCommandBlock
    public int listPotions(CommandSender sender, @CommandArgument("class") Class clas) {
        List<String> list = clas.getBlockedPotions().stream().map(enchantment -> enchantment.getKey().getNamespace() + ":" + enchantment.getKey().getKey()).toList();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.title-potion", "&eBlocked potions for class %class%:").replaceAll("%class%", clas.getName())));

        for (String string : list)
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.regex", "&5- &d%item%").replaceAll("%item%", string)));
        return 0;
    }

    @Command(value = "class.a:0:block.a:0:potion.a:0:remove", permission = "classes.block.potion.remove", description = "Remove a potion")
    @BlockCommandBlock
    public int removePotion(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument PotionEffectType potion) {
        List<PotionEffectType> list = clas.getBlockedPotions();
        if (!list.contains(potion)) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-potion-block.not-added", "&cThat potion is not blocked for that class.")));
            return 1;
        }
        list.remove(potion);
        clas.setBlockedPotions(list);
        clas.clean();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-potion-block.done", "&aSuccessfully unblocked &b%potion% &afor &b%class%&a!")
                .replace("%potion%", potion.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }
}