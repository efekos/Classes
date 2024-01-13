package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SetDescriptionNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> list = new ArrayList<>(args);
        list.remove(0);
        String desc = String.join(" ", list);

        clas.setDescription(desc);
        Main.CLASSES.update(clas.getUniqueId(),clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set-description.done","&aSuccessfully changed description to &b%desc%&a!")
                .replace("%desc%",desc)
        ));
    }
}
