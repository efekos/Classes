package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AddPotionBlockNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getBlockedPotions().contains(PotionEffectType.getByKey(NamespacedKey.fromString(args.get(1))))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-potion-block.already","&cThat potion is already blocked for that class.")));
            return;
        }
        clas.getBlockedPotions().add(PotionEffectType.getByKey(NamespacedKey.fromString(args.get(1))));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-potion-block.done","&aSuccessfully blocked &b%potion% &afor &b%class%&a!")
                .replace("%potion%",args.get(1))
                .replace("%class%",clas.getName())
        ));
    }
}
