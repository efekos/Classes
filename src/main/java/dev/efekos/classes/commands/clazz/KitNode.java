package dev.efekos.classes.commands.clazz;

import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KitNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        if(context.isSenderPlayer()){
            Player player = (Player) sender;

        } else if(context.isSenderConsole()){

        }
    }
}
