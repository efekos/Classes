package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class UpdateKitNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        if (!context.isSenderPlayer()) {
            sender.sendMessage(TranslateManager.translateColors(Simpler.getMessageConfiguration().ONLY_PLAYER));
            return;
        }

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if (clas == null) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class", "&cThere is no class with that name.")));
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("classes.kit.update")) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.no-perm", "&cYou can't update a kit.")));
            return;
        }
        List<ItemStack> stacks = Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).toList();

        for (ItemStack i : stacks) {

            Set<Enchantment> enchantments = i.getEnchantments().keySet();
            if (i.getItemMeta() instanceof PotionMeta potionMeta) {
                if (potionMeta.getCustomEffects().stream().map(PotionEffect::getType).anyMatch(clas.getBlockedPotions()::contains))
                    continue;
            }

            if (clas.getBlockedMaterials().contains(i.getType()) || clas.getBlockedEnchantments().stream().anyMatch(enchantments::contains)) {
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.not-compatible", "&cAt least one of the items in your inventory is incompatible for that class.")));
                return;
            }
        }

        clas.setKitItems(stacks);
        Main.CLASSES.update(clas.getUniqueId(), clas);
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.done", "&aSuccessfully put all your inventory to the kit of class &b%class%&a!")
                .replace("%class%", clas.getName())));
    }
}
