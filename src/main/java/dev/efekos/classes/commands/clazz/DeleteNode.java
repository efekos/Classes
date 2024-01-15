package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DeleteNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        if (Main.CLASSES.getAll().stream().noneMatch(aClass -> aClass.getName().equals(args.get(0)))) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("delete.unexists", "&cA class called &b%name% &calready doesn't exists.").replace("%name%", args.get(0))));
            return;
        }

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));

        ClassManager.getDatas().forEach((uuid, playerData) -> {
            if(playerData.getCurrentClass()==null)return;
            if(playerData.getCurrentClass().equals(clas.getUniqueId())){
                Player p = Bukkit.getPlayer(uuid);
                if(p==null)return;
                clas.getPerks().forEach(perkApplier -> Main.PERK_REGISTRY.get(perkApplier.getPerkId()).degrade(p));
                clas.getModifiers().forEach(modifierApplier -> Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId()).deapply(p));
                playerData.setCurrentClass(null);
                playerData.getClassLevels().remove(clas.getUniqueId());
                p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.notification","&eThe &b%class% &eclass you're in just got deleted by a server admin.")));
            }
        });

        Main.CLASSES.delete(clas.getUniqueId());

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.done","&aSuccessfully deleted the class &b%name%&a! Keep in mind that some users of this class might still have some of its modifiers activated until respawn.").replace("%name%",args.get(0))));
    }
}
