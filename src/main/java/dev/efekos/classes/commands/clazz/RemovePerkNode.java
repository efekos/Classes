package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class RemovePerkNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getPerks().stream().allMatch(modifierApplier -> !modifierApplier.getPerkId().equals(NamespacedKey.fromString(args.get(3))))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.not-added","&cThat perk is not in that class.")));
            return;
        }
        clas.setPerks(clas.getPerks().stream().filter(modifierApplier -> !modifierApplier.getPerkId().equals(NamespacedKey.fromString(args.get(3)))).toList());
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.done","&aSuccessfully removed &b%perk% &from &b%class%&a!")
                .replace("%perk%",args.get(3))
                .replace("%class%",clas.getName())
        ));
    }
}
