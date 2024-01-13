package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.PerkApplier;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddPerkNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getPerks().stream().anyMatch(modifierApplier -> modifierApplier.getPerkId().equals(NamespacedKey.fromString(args.get(1))))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.already","&cThat perk is already added for that class. Consider re-adding it using both &b/class removeperk &cand &b/class addperk")));
            return;
        }
        clas.getPerks().add(new PerkApplier(NamespacedKey.fromString(args.get(1))));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.done","&aSuccessfully added &b%perk% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
                .replace("%perk%",args.get(1))
                .replace("%class%",clas.getName())
        ));
    }
}
