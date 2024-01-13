package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.PerkApplier;
import dev.efekos.classes.menu.ChooseClassMenu;
import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        CommandSender sender = context.sender();

        if(context.isSenderPlayer()){
            Player player = (Player) sender;

            if(!ClassManager.hasClass(player.getUniqueId())){
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.leave.not-class","&cYou are not in a class.")));
                return;
            }
            dev.efekos.classes.data.Class clas = ClassManager.getClass(player.getUniqueId());

            clas.getModifiers().forEach(modifierApplier -> {
                IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
                if(IModifier !=null) IModifier.deapply(player);
            });
            for (PerkApplier perk : clas.getPerks()) {
                Main.PERK_REGISTRY.get(perk.getPerkId()).degrade(player);
            }

            ClassManager.removeClass(player.getUniqueId());

            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.leave.done","&aSuccessfully left your class!")));

            if(Main.CONFIG.getBoolean("class-required",true)) MenuManager.Open(player, ChooseClassMenu.class);

        } else if(context.isSenderConsole()){
            sender.sendMessage(TranslateManager.translateColors(Simpler.getMessageConfiguration().ONLY_PLAYER));
        }
    }
}
