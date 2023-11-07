package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.commands.arguments.ModifierIDArgument;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Command(name = "removemodifier",description = "Remove a modifier from a class",permission = "classes.removemodifier")
public final class RemoveModifier extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new ModifierIDArgument());
    }

    public RemoveModifier(@NotNull String name) {
        super(name);
    }

    public RemoveModifier(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getModifiers().stream().noneMatch(modifierApplier -> modifierApplier.getModifierId().equals(NamespacedKey.fromString(args[1])))){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.not-added","&cThat modifier is not in that class.")));
            return;
        }
        clas.setModifiers(clas.getModifiers().stream().filter(modifierApplier -> modifierApplier.getModifierId()!=NamespacedKey.fromString(args[1])).collect(Collectors.toList()));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.done","&aSuccessfully removed &b%modifier% &from &b%class%&a!")
                .replace("%modifier%",args[1])
                .replace("%class%",clas.getName())
        ));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getModifiers().stream().noneMatch(modifierApplier -> modifierApplier.getModifierId().equals(NamespacedKey.fromString(args[1])))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.not-added","&cThat modifier is not in that class.")));
            return;
        }
        clas.setModifiers(clas.getModifiers().stream().filter(modifierApplier -> modifierApplier.getModifierId()!=NamespacedKey.fromString(args[1])).collect(Collectors.toList()));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-modifier.done","&aSuccessfully removed &b%modifier% &from &b%class%&a!")
                .replace("%modifier%",args[1])
                .replace("%class%",clas.getName())
        ));
    }
}
