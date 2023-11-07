package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "modifiers",description = "List of all the modifiers in a class",permission = "classes.modifiers")
public final class Modifiers extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED));
    }

    public Modifiers(@NotNull String name) {
        super(name);
    }

    public Modifiers(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> modifierStrings = new ArrayList<>();
        clas.getModifiers().forEach(modifierApplier -> {
            IModifier IModifier = ModifierRegistry.getInstance().get(modifierApplier.getModifierId());
            if(IModifier ==null)return;

            modifierStrings.add(ChatColor.AQUA+modifierApplier.getModifierId().toString() + " &6 - &e"+ IModifier.getDescription(ClassManager.getLevel(player.getUniqueId()),modifierApplier.getValue()));
        });

        for (String s : modifierStrings) {
            player.sendMessage(TranslateManager.translateColors(s));
        }
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> modifierStrings = new ArrayList<>();
        clas.getModifiers().forEach(modifierApplier -> {
            IModifier IModifier = ModifierRegistry.getInstance().get(modifierApplier.getModifierId());
            if(IModifier ==null)return;

            modifierStrings.add(ChatColor.AQUA+modifierApplier.getModifierId().toString() + " &6 - &e"+ IModifier.getDescription(1,modifierApplier.getValue()));
        });

        for (String s : modifierStrings) {
            sender.sendMessage(TranslateManager.translateColors(s));
        }
    }
}
