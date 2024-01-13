package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.menu.ClassInfoMenu;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoNode implements CommandExecutive{
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();

        if(context.sender() instanceof Player player){

            dev.efekos.classes.data.Class clas = args!=null&& !args.isEmpty() ? Main.getClassByName(args.get(0)): ClassManager.getClass(player.getUniqueId());
            if(clas==null){
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class-i","&cThere is no class with that name or you don't have a class.")));
                return;
            }

            MenuData data = MenuManager.getMenuData(player);
            data.set("class",clas.getUniqueId());
            MenuManager.updateMenuData(player,data);

            MenuManager.Open(player, ClassInfoMenu.class);
        }

    }
}
