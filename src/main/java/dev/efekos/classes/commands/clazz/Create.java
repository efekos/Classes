package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.registry.ClassesCriterias;
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

import java.util.List;

@Command(name = "create",description = "Creates a new class",permission = "classes.create")
public final class Create extends SubCommand {
    public Create(@NotNull String name) {
        super(name);
    }

    public Create(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new StringArgument("name", ArgumentPriority.REQUIRED,4,32));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class newClass = new dev.efekos.classes.data.Class(args[0],"Another class", ClassesCriterias.TAKE_DAMAGE);

        if (Main.CLASSES.getAll().stream().filter(aClass -> aClass.getName().equals(newClass.getName())).count()>0) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.exists", "&cA class called &b%name% &calready exists.").replace("%name%", args[0])));
            return;
        }

        Main.CLASSES.add(newClass);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.done","&aSuccessfully created a class called &b%name%&a!").replace("%name%",args[0])));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class newClass = new dev.efekos.classes.data.Class(args[0],"Another class", ClassesCriterias.TAKE_DAMAGE);

        if (Main.CLASSES.getAll().stream().filter(aClass -> aClass.getName().equals(newClass.getName())).count()>0) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.exists", "&cA class called &b%name% &calready exists.").replace("%name%", args[0])));
            return;
        }

        Main.CLASSES.add(newClass);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.create.done","&aSuccessfully created a class called &b%name%&a!").replace("%name%",args[0])));
    }
}
