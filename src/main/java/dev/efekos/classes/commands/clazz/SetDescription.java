package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.syntax.impl.StringArgument;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(name = "setdescription",description = "Change description of a class.",permission = "classes.setdescription")
public final class SetDescription extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new StringArgument("desc",ArgumentPriority.REQUIRED,1,Integer.MAX_VALUE));
    }

    public SetDescription(@NotNull String name) {
        super(name);
    }

    public SetDescription(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> list = new ArrayList<>(Arrays.stream(args).toList());
        list.remove(0);
        String desc = String.join(" ", list);

        clas.setDescription(desc);
        Main.CLASSES.update(clas.getUniqueId(),clas);
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set-description.done","&aSuccessfully changed description to &b%desc%&a!")
                .replace("%desc%",desc)
        ));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> list = new ArrayList<>(Arrays.stream(args).toList());
        list.remove(0);
        String desc = String.join(" ", list);

        clas.setDescription(desc);
        Main.CLASSES.update(clas.getUniqueId(),clas);
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.set-description.done","&aSuccessfully changed description to &b%desc%&a!")
                .replace("%desc%",desc)
        ));
    }
}
