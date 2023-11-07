package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.commands.arguments.PerkIDArgument;
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

@Command(name = "removeperk",description = "Remove a perk from a class",permission = "classes.removeperk")
public final class RemovePerk extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new PerkIDArgument());
    }

    public RemovePerk(@NotNull String name) {
        super(name);
    }

    public RemovePerk(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getPerks().stream().allMatch(modifierApplier -> !modifierApplier.getPerkId().equals(NamespacedKey.fromString(args[1])))){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.not-added","&cThat perk is not in that class.")));
            return;
        }
        clas.setPerks(clas.getPerks().stream().filter(modifierApplier -> modifierApplier.getPerkId()!=NamespacedKey.fromString(args[1])).collect(Collectors.toList()));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.done","&aSuccessfully removed &b%perk% &from &b%class%&a!")
                .replace("%perk%",args[1])
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
        if(clas.getPerks().stream().allMatch(modifierApplier -> !modifierApplier.getPerkId().equals(NamespacedKey.fromString(args[1])))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.not-added","&cThat perk is not in that class.")));
            return;
        }
        clas.setPerks(clas.getPerks().stream().filter(modifierApplier -> modifierApplier.getPerkId()!=NamespacedKey.fromString(args[1])).collect(Collectors.toList()));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-perk.done","&aSuccessfully removed &b%perk% &from &b%class%&a!")
                .replace("%perk%",args[1])
                .replace("%class%",clas.getName())
        ));
    }
}
