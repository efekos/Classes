package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class MaterialListNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if (clas == null) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class", "&cThere is no class with that name.")));
            return;
        }

        List<String> list = clas.getBlockedMaterials().stream().map(enchantment -> enchantment.getKey().getNamespace() + ":" + enchantment.getKey().getKey()).toList();

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.title-material", "&eBlocked materials for class %class%:").replaceAll("%class%", clas.getName())));

        for (String string : list) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.block-list.regex", "&5- &d%item%").replaceAll("%item%", string)));
        }
    }
}
