package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class GetKitNode implements CommandExecutive {
    @Override
    public final void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if (clas == null) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class", "&cThere is no class with that name.")));
            return;
        }

        if (!context.isSenderPlayer()) {
            sender.sendMessage(TranslateManager.translateColors(Simpler.getMessageConfiguration().ONLY_PLAYER));
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("classes.kit.get")) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.no-perm", "&cYou can't get a kit.")));
            return;
        }
        if (!player.hasPermission("classes.admin") && ClassManager.getClass(player.getUniqueId()).getUniqueId() != clas.getUniqueId()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.not-current", "&cYou can only get kit of your current class.")));
            return;
        }
        int space = 27;

        for (int i = 0; i < 27; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (Objects.nonNull(item)) space--;
        }

        if (space < clas.getKitItems().size()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.nes", "&cThere is no enough space in your inventory to get the kit.")));
            return;
        }
        for (ItemStack item : clas.getKitItems()) {
            player.getInventory().addItem(item);
        }
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.kit.get.done", "&aSuccessfully got the kit!")));
    }
}
