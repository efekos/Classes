package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.registry.ClassesCriterias;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class newClass = new dev.efekos.classes.data.Class(args.get(1),"Another class", ClassesCriterias.TAKE_DAMAGE);

        if (Main.CLASSES.getAll().stream().filter(aClass -> aClass.getName().equals(newClass.getName())).count()>0) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.exists", "&cA class called &b%name% &calready exists.").replace("%name%", args.get(1))));
            return;
        }

        Main.CLASSES.add(newClass);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.done","&aSuccessfully created a class called &b%name%&a!").replace("%name%",args.get(1))));
    }
}
