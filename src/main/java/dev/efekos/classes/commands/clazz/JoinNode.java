package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.PerkApplier;
import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        if(context.isSenderPlayer()){
            Player player = (Player) sender;

            dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
            if(clas==null){
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
                return;
            }

            if(ClassManager.hasClass(player.getUniqueId())){
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.join.another", "&cYou are already in another class. Consider leaving it with &b/class leave &cbefore joining a new one.")));
                return;
            }

            ClassManager.setClass(player.getUniqueId(),clas);

            clas.getModifiers().forEach(modifierApplier -> {
                IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
                if(IModifier !=null) IModifier.apply(player,ClassManager.getLevel(player.getUniqueId()),modifierApplier.getValue());
            });
            for (PerkApplier perk : clas.getPerks()) {
                Main.PERK_REGISTRY.get(perk.getPerkId()).grant(player, ClassManager.getLevel(player.getUniqueId()));
            }

            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.join.done","&aSuccessfully joined &b%class% &aclass! Check out your pros and cons by typing &b/class info&a.").replace("%class%",clas.getName())));
        } else if(context.isSenderConsole()){

            sender.sendMessage(TranslateManager.translateColors(Simpler.getMessageConfiguration().ONLY_PLAYER));

        }
    }
}
