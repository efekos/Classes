package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.ModifierApplier;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AddModifierNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) { // /clas <class> modifier add <modifier> <value>
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        NamespacedKey key = NamespacedKey.fromString(args.get(3));
        if(clas.getModifiers().stream().anyMatch(modifierApplier -> modifierApplier.getModifierId().equals(key))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.already","&cThat modifier is already added for that class. Consider re-adding it using both &b/class removemodifier &cand &b/class addmodifier")));
            return;
        }
        assert key!=null;

        clas.getModifiers().add(new ModifierApplier(key,Double.parseDouble(args.get(4))));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.done","&aSuccessfully added &b%modifier% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
                .replace("%modifier%",args.get(3))
                .replace("%class%",clas.getName())
        ));
    }
}
