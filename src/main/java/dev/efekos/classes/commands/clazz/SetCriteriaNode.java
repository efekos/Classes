package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class SetCriteriaNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        clas.setLevelCriteria(Main.CRITERIA_REGISTRY.get(NamespacedKey.fromString(args.get(3))));
        Main.CLASSES.update(clas.getUniqueId(),clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set-criteria.done","&aSuccessfully changed level criteria to &b%cri%&a!")
                .replace("%cri%",args.get(1))
        ));
    }
}
