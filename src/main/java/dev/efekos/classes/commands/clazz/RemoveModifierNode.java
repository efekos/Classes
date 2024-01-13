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

public class RemoveModifierNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getModifiers().stream().noneMatch(modifierApplier -> modifierApplier.getModifierId().equals(NamespacedKey.fromString(args.get(1))))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.not-added","&cThat modifier is not in that class.")));
            return;
        }
        clas.setModifiers(clas.getModifiers().stream().filter(modifierApplier -> modifierApplier.getModifierId()!=NamespacedKey.fromString(args.get(1))).toList());
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.done","&aSuccessfully removed &b%modifier% &from &b%class%&a!")
                .replace("%modifier%",args.get(1))
                .replace("%class%",clas.getName())
        ));
    }
}
