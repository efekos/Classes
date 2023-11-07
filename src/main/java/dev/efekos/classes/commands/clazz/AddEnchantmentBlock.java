package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.commands.arguments.EnchantmentNameArgument;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "addenchantmentblock",description = "Add an enchantment block for a class",permission = "classes.addblock.enchantment")
public final class AddEnchantmentBlock extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new EnchantmentNameArgument(ArgumentPriority.REQUIRED,"enchantment"));
    }

    public AddEnchantmentBlock(@NotNull String name) {
        super(name);
    }

    public AddEnchantmentBlock(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getBlockedEnchantments().contains(Enchantment.getByKey(NamespacedKey.fromString(args[1])))){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.already","&cThat enchantment is already blocked for that class.")));
            return;
        }
        clas.getBlockedEnchantments().add(Enchantment.getByKey(NamespacedKey.fromString(args[1])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.done","&aSuccessfully blocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%",args[1])
                .replace("%class%",clas.getName())
        ));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getBlockedEnchantments().contains(Enchantment.getByKey(NamespacedKey.fromString(args[1])))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.already","&cThat enchantment is already blocked for that class.")));
            return;
        }
        clas.getBlockedEnchantments().add(Enchantment.getByKey(NamespacedKey.fromString(args[1])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.done","&aSuccessfully blocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%",args[1])
                .replace("%class%",clas.getName())
        ));
    }
}
