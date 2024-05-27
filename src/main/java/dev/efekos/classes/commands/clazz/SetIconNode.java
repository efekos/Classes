package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.items.ItemContent;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public final class SetIconNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        if (!context.isSenderPlayer()) {
            context.sender().sendMessage(TranslateManager.translateColors(Simpler.getMessageConfiguration().ONLY_PLAYER));
            return;
        }

        Player player = ((Player) context.sender());
        List<String> args = context.args();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if (clas == null) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class", "&cThere is no class with that name.")));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set.icon.empty", "&cYou are not holding an item in your hand")));
            return;
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
    }
}