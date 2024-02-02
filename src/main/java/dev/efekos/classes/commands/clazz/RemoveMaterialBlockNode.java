package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public final class RemoveMaterialBlockNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(!clas.getBlockedMaterials().contains(Utilities.getMaterialByKey(args.get(4)))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-material-block.not-added","&cThat material is not blocked for that class.")));
            return;
        }
        clas.getBlockedMaterials().remove(Utilities.getMaterialByKey(args.get(4)));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-material-block.done","&aSuccessfully unblocked &b%material% &afor &b%class%&a!")
                .replace("%material%",args.get(4))
                .replace("%class%",clas.getName())
        ));
    }
}
