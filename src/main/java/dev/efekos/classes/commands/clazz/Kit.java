package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.syntax.impl.ListArgument;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Command(name = "kit",description = "Manage a class kit",permission = "classes.kit")
public final class Kit extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new ListArgument("clear/update/get",ArgumentPriority.REQUIRED,"clear","update","get"));
    }

    public Kit(@NotNull String name) {
        super(name);
    }

    public Kit(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }

        switch (args[1]){
            case "clear" -> {
                if(!player.hasPermission("classes.kit.clear")){
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.no-perm","&cYou can't clear a kit.")));
                    return;
                }
                clas.setKitItems(new ArrayList<>());
                Main.CLASSES.update(clas.getUniqueId(),clas);
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.done","&aSuccessfully cleared the kit items!")));
            }
            case "update" -> {
                if(!player.hasPermission("classes.kit.update")){
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.no-perm","&cYou can't update a kit.")));
                    return;
                }
                List<ItemStack> stacks = Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).toList();

                for (ItemStack i:stacks){

                    Set<Enchantment> enchantments = i.getEnchantments().keySet();
                    if(i.getItemMeta() instanceof PotionMeta potionMeta){
                        if(potionMeta.getCustomEffects().stream().map(PotionEffect::getType).anyMatch(clas.getBlockedPotions()::contains))continue;
                    }

                    if (clas.getBlockedMaterials().contains(i.getType()) || clas.getBlockedEnchantments().stream().anyMatch(enchantments::contains)){
                        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.not-compatible", "&cAt least one of the items in your inventory is incompatible for that class.")));
                        return;
                    }
                }

                clas.setKitItems(stacks);
                Main.CLASSES.update(clas.getUniqueId(),clas);
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.update.done","&aSuccessfully put all your inventory to the kit of class &b%class%&a!")
                        .replace("%class%",clas.getName())));
            }
            case "get" -> {
                if(!player.hasPermission("classes.kit.get")){
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.no-perm","&cYou can't get a kit.")));
                    return;
                }
                if(!player.hasPermission("classes.admin")&& ClassManager.getClass(player.getUniqueId()).getUniqueId()!=clas.getUniqueId()){
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.not-current", "&cYou can only get kit of your current class.")));
                    return;
                }
                int space = 27;

                for (int i = 0; i < 27; i++) {
                    ItemStack item = player.getInventory().getItem(i);
                    if(Objects.nonNull(item)) space--;
                }

                if (space<clas.getKitItems().size()) {
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.nes","&cThere is no enough space in your inventory to get the kit.")));
                    return;
                }
                for (ItemStack item : clas.getKitItems()) {
                    player.getInventory().addItem(item);
                }
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.done","&aSuccessfully got the kit!")));
            }
        }

    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }

        switch (args[1]){
            case "clear" -> {
                clas.setKitItems(new ArrayList<>());
                Main.CLASSES.update(clas.getUniqueId(),clas);
                sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.done","&aSuccessfully cleared the kit items!")));
            }
            case "update", "get" -> {
                sender.sendMessage();
                sender.sendMessage("Only command you can use from console is /class kit <c> clear.");
            }
        }
    }
}
