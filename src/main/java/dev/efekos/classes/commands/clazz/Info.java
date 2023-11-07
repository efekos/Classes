package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.PerkApplier;
import dev.efekos.classes.menu.ClassInfoMenu;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "info",description = "Information about a class",playerOnly = true,permission = "classes.info")
public final class Info extends SubCommand {
    public Info(@NotNull String name) {
        super(name);
    }

    public Info(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class", ArgumentPriority.OPTIONAL));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = args!=null&&args.length>0? Main.getClassByName(args[0]):ClassManager.getClass(player.getUniqueId());
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class-i","&cThere is no class with that name or you don't have a class.")));
            return;
        }

        MenuData data = MenuManager.getMenuData(player);
        data.set("class",clas.getUniqueId());
        MenuManager.updateMenuData(player,data);

        MenuManager.Open(player, ClassInfoMenu.class);

    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class clas = args!=null&&args.length>0? Main.getClassByName(args[0]):null;
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }

        sender.sendMessage(ChatColor.RED+"This command normally opens up a menu, which means that you can't see anything from console.");
    }
}
