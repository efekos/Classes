package dev.efekos.classes.commands;

import dev.efekos.arn.annotation.Command;
import dev.efekos.arn.annotation.CommandArgument;
import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.annotation.block.BlockCommandBlock;
import dev.efekos.arn.annotation.block.BlockConsole;
import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.*;

@Container
public class KitCommands {

    @Command("class.a:0:kit.a:0:clear")
    @BlockCommandBlock
    public int clearKit(CommandSender sender, @CommandArgument("class")Class clas) {
        if (!sender.hasPermission("classes.kit.clear")) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.no-perm", "&cYou can't clear a kit.")));
            return 1;
        }
        clas.setKitItems(new ArrayList<>());
        Main.CLASSES.update(clas.getUniqueId(), clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.done", "&aSuccessfully cleared the kit items!")));
        return 0;
    }

    @Command("class.a:0:kit.a:0:get")
    @BlockCommandBlock @BlockConsole
    public int getKit(Player player, @CommandArgument("class")Class clas) {
        if (!player.hasPermission("classes.kit.get")) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.no-perm", "&cYou can't get a kit.")));
            return 1;
        }
        if (!player.hasPermission("classes.admin") && ClassManager.getClass(player.getUniqueId()).getUniqueId() != clas.getUniqueId()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.not-current", "&cYou can only get kit of your current class.")));
            return 1;
        }
        int space = 27;

        for (int i = 0; i < 27; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (Objects.nonNull(item)) space--;
        }

        if (space < clas.getKitItems().size()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.nes", "&cThere is no enough space in your inventory to get the kit.")));
            return 1;
        }
        for (ItemStack item : clas.getKitItems()) player.getInventory().addItem(item);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.done", "&aSuccessfully got the kit!")));
        return 0;
    }

    @Command("class.a:0:kit.a:0:update")
    @BlockCommandBlock @BlockConsole
    public int updateKit(Player player, @CommandArgument("class")Class clas) {
        if (!player.hasPermission("classes.kit.update")) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.no-perm", "&cYou can't update a kit.")));
            return 1;
        }
        List<ItemStack> stacks = Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).toList();

        for (ItemStack i : stacks) {

            Set<Enchantment> enchantments = i.getEnchantments().keySet();
            if (i.getItemMeta() instanceof PotionMeta potionMeta) {
                if (potionMeta.getCustomEffects().stream().map(PotionEffect::getType).anyMatch(clas.getBlockedPotions()::contains)){
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.not-compatible", "&cAt least one of the items in your inventory is incompatible for that class.")));
                    return 1;
                }
            }

            if (clas.getBlockedMaterials().contains(i.getType()) || clas.getBlockedEnchantments().stream().anyMatch(enchantments::contains)) {
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.not-compatible", "&cAt least one of the items in your inventory is incompatible for that class.")));
                return 1;
            }
        }

        clas.setKitItems(stacks);
        Main.CLASSES.update(clas.getUniqueId(), clas);
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.done", "&aSuccessfully put all your inventory to the kit of class &b%class%&a!")
                .replace("%class%", clas.getName())));
        return 0;
    }

}
