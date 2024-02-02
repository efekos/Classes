package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.List;

public final class AddEnchantmentBlockNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getBlockedEnchantments().contains(Enchantment.getByKey(NamespacedKey.fromString(args.get(4))))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.already","&cThat enchantment is already blocked for that class.")));
            return;
        }
        clas.getBlockedEnchantments().add(Enchantment.getByKey(NamespacedKey.fromString(args.get(4))));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-enchantment-block.done","&aSuccessfully blocked &b%enchant% &afor &b%class%&a!")
                .replace("%enchant%",args.get(4))
                .replace("%class%",clas.getName())
        ));
    }
}
