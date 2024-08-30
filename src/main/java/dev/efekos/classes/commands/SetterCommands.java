package dev.efekos.classes.commands;

import dev.efekos.arn.annotation.Command;
import dev.efekos.arn.annotation.CommandArgument;
import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.annotation.block.BlockCommandBlock;
import dev.efekos.arn.annotation.modifier.Greedy;
import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.ILevelCriteria;
import dev.efekos.classes.data.Class;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

@Container
public class SetterCommands {

    @Command(value = "class.a:0:set.a:0:criteria", permission = "classes.set.criteria", description = "Change criteria of a class.")
    @BlockCommandBlock
    public int setCriteria(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument ILevelCriteria criteria) {
        clas.setLevelCriteria(criteria);
        Main.CLASSES.update(clas.getUniqueId(), clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set-criteria.done", "&aSuccessfully changed level criteria to &b%cri%&a!")
                .replace("%cri%", Main.CRITERIA_REGISTRY.idOf(criteria).getKey())
        ));
        return 0;
    }

    @Command(value = "class.a:0:set.a:0:description", permission = "classes.set.description", description = "Change description of a class.")
    @BlockCommandBlock
    public int setDescription(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument("description") @Greedy String description) {
        clas.setDescription(description);
        Main.CLASSES.update(clas.getUniqueId(), clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set-description.done", "&aSuccessfully changed description to &b%desc%&a!")
                .replace("%desc%", description)
        ));
        return 0;
    }

    @Command(value = "class.a:0:set.a:0:icon", permission = "classes.set.icon", description = "Change icon of a class.")
    @BlockCommandBlock
    public int setIconManually(Player player, @CommandArgument("class") Class clas, @CommandArgument ItemStack item) {

        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set.icon.empty-m", "&cYou can't use air")));
            return 1;
        }

        clas.setIcon(item);
        Main.CLASSES.update(clas.getUniqueId(), clas);

        BaseComponent component = item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).hasDisplayName() ? new TextComponent(item.getItemMeta().getDisplayName()) : TranslateManager.translateMaterial(item.getType());
        ItemStack cloned = item.clone();
        cloned.setAmount(1);
        ItemMeta meta = cloned.getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) {
            assert meta != null;
            meta.addItemFlags(flag);
        }

        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item(item.getType().getKey().toString(),1, ItemTag.ofNbt(meta.getAsString()))));

        player.spigot().sendMessage(Utilities.makeComponentsForValue(TranslateManager.translateColors(
                Main.LANG.getString("commands.set.icon.done", "&aSuccessfully changed &b%class%&a's icon to a(n) &f[&f%item%&f]&a!")
                        .replace("%class%", clas.getName())), "%item%", component));
        return 0;
    }

    @Command(value = "class.a:0:set.a:0:icon", permission = "classes.set.icon", description = "Change icon of a class.")
    @BlockCommandBlock
    public int setIcon(Player player, @CommandArgument("class") Class clas) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set.icon.empty", "&cYou are not holding an item in your hand")));
            return 1;
        }

        clas.setIcon(item);
        Main.CLASSES.update(clas.getUniqueId(), clas);

        BaseComponent component = item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).hasDisplayName() ? new TextComponent(item.getItemMeta().getDisplayName()) : TranslateManager.translateMaterial(item.getType());
        ItemStack cloned = item.clone();
        cloned.setAmount(1);
        ItemMeta meta = cloned.getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) {
            assert meta != null;
            meta.addItemFlags(flag);
        }
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, ItemContent.from(cloned)));

        player.spigot().sendMessage(Utilities.makeComponentsForValue(TranslateManager.translateColors(
                Main.LANG.getString("commands.set.icon.done", "&aSuccessfully changed &b%class%&a's icon to a(n) &f[&f%item%&f]&a!")
                        .replace("%class%", clas.getName())), "%item%", component));
        return 0;
    }

}
