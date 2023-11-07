package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.commands.arguments.PerkIDArgument;
import dev.efekos.classes.data.PerkApplier;
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

@Command(name = "addperk",description = "Add a perk to a class",permission = "classes.addperk")
public final class AddPerk extends SubCommand {
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

    public AddPerk(@NotNull String name) {
        super(name);
    }

    public AddPerk(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(clas.getPerks().stream().anyMatch(modifierApplier -> modifierApplier.getPerkId().equals(NamespacedKey.fromString(args[1])))){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.already","&cThat perk is already added for that class. Consider re-adding it using both &b/class removeperk &cand &b/class addperk")));
            return;
        }
        clas.getPerks().add(new PerkApplier(NamespacedKey.fromString(args[1])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.done","&aSuccessfully added &b%perk% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
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
        if(clas.getPerks().stream().anyMatch(modifierApplier -> modifierApplier.getPerkId().equals(NamespacedKey.fromString(args[1])))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.already","&cThat perk is already added for that class. Consider re-adding it using both &b/class removeperk &cand &b/class addperk")));
            return;
        }
        clas.getPerks().add(new PerkApplier(NamespacedKey.fromString(args[1])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-perk.done","&aSuccessfully added &b%perk% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
                .replace("%perk%",args[1])
                .replace("%class%",clas.getName())
        ));
    }
}
