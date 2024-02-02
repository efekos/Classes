package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.data.PerkApplier;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class PerksNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> perkStrings = new ArrayList<>();
        for (PerkApplier applier : clas.getPerks()) {
            IPerk perk = Main.PERK_REGISTRY.get(applier.getPerkId());
            if(perk==null)continue;
            perkStrings.add(ChatColor.AQUA+applier.getPerkId().toString() +"&6 - &e"+ perk.getDescription(1));
        }

        for (String s : perkStrings) {
            sender.sendMessage(TranslateManager.translateColors(s));
        }
    }
}
