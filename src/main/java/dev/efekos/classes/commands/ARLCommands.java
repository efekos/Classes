package dev.efekos.classes.commands;

import dev.efekos.arn.annotation.Command;
import dev.efekos.arn.annotation.CommandArgument;
import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.annotation.block.BlockCommandBlock;
import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.List;

@Container
public class ARLCommands {

    // TODO descriptions
    // TODO permissions
    // TODO exceptions

    @Command(value = "class.a:0:enchantment.a:0:add")
    @BlockCommandBlock
    public int addEnchantment(CommandSender sender, @CommandArgument("class") Class clas, @CommandArgument Enchantment enchantment){

        if (clas.getBlockedEnchantments().contains(enchantment)) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.already", "&cThat enchantment is already blocked for that class.")));
            return 1;
        }
        clas.getBlockedEnchantments().add(enchantment);
        Main.CLASSES.update(clas.getUniqueId(), clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.done", "&aSuccessfully blocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%", enchantment.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:enchantment.a:0:remove")
    @BlockCommandBlock
    public int removeEnchantment(CommandSender sender, @CommandArgument("class") Class clas,@CommandArgument Enchantment enchantment){

        if (!clas.getBlockedEnchantments().contains(enchantment)) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-enchantment-block.not-added", "&cThat enchantment is not blocked for that class.")));
            return 1;
        }
        clas.getBlockedEnchantments().remove(enchantment);
        Main.CLASSES.update(clas.getUniqueId(), clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-enchantment-block.done", "&aSuccessfully unblocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%", enchantment.getKey().toString())
                .replace("%class%", clas.getName())
        ));
        return 0;
    }

    @Command(value = "class.a:0:enchantment.a:0:list")
    @BlockCommandBlock
    public int listEnchants(CommandSender sender, @CommandArgument("class") Class clas){
        List<String> list = clas.getBlockedEnchantments().stream().map(enchantment -> enchantment.getKey().getNamespace() + ":" + enchantment.getKey().getKey()).toList();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.title-enchantment", "&eBlocked enchantments for class %class%:").replaceAll("%class%", clas.getName())));

        for (String string : list)
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.regex", "&5- &d%item%").replaceAll("%item%", string)));

        return 0;
    }
}