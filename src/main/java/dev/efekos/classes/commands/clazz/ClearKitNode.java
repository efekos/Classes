package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClearKitNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }

        if(!sender.hasPermission("classes.kit.clear")){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.no-perm","&cYou can't clear a kit.")));
            return;
        }
        clas.setKitItems(new ArrayList<>());
        Main.CLASSES.update(clas.getUniqueId(),clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.clear.done","&aSuccessfully cleared the kit items!")));
    }
}
