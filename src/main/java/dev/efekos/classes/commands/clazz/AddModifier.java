package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.commands.arguments.DoubleArgument;
import dev.efekos.classes.commands.arguments.ModifierIDArgument;
import dev.efekos.classes.data.ModifierApplier;
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

@Command(name = "addmodifier",description = "Add a modifier for a class",permission = "classes.addmodifier")
public final class AddModifier extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new ModifierIDArgument())
                .withArgument(new DoubleArgument("value",ArgumentPriority.REQUIRED));
    }

    public AddModifier(@NotNull String name) {
        super(name);
    }

    public AddModifier(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        NamespacedKey key = NamespacedKey.fromString(args[1]);
        if(clas.getModifiers().stream().anyMatch(modifierApplier -> modifierApplier.getModifierId().equals(key))){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.already","&cThat modifier is already added for that class. Consider re-adding it using both &b/class removemodifier &cand &b/class addmodifier")));
            return;
        }
        assert key!=null;

        clas.getModifiers().add(new ModifierApplier(key,Double.parseDouble(args[2])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.done","&aSuccessfully added &b%modifier% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
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
        NamespacedKey key = NamespacedKey.fromString(args[1]);
        if(clas.getModifiers().stream().anyMatch(modifierApplier -> modifierApplier.getModifierId().equals(key))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.already","&cThat modifier is already added for that class. Consider re-adding it using both &b/class removemodifier &cand &b/class addmodifier")));
            return;
        }
        assert key!=null;

        clas.getModifiers().add(new ModifierApplier(key,Double.parseDouble(args[2])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.add-modifier.done","&aSuccessfully added &b%modifier% &afor &b%class%&a! Keep in mind that it will be shown as a pro/con at &b/class info %class%&a.")
                .replace("%modifier%",args[1])
                .replace("%class%",clas.getName())
        ));
    }
}
