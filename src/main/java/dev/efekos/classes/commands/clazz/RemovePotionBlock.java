package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.commands.arguments.PotionNameArgument;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "removepotionblock",description = "Remove a potion block from a class",permission = "classes.removeblock.potion")
public final class RemovePotionBlock extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED))
                .withArgument(new PotionNameArgument(ArgumentPriority.REQUIRED,"potion"));
    }

    public RemovePotionBlock(@NotNull String name) {
        super(name);
    }

    public RemovePotionBlock(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        if(!clas.getBlockedPotions().contains(PotionEffectType.getByKey(NamespacedKey.fromString(args[1])))){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-potion-block.not-added","&cThat potion is not blocked for that class.")));
            return;
        }
        clas.getBlockedPotions().remove(PotionEffectType.getByKey(NamespacedKey.fromString(args[1])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-potion-block.done","&aSuccessfully unblocked &b%potion% &afor &b%class%&a!")
                .replace("%potion%",args[1])
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
        if(!clas.getBlockedPotions().contains(PotionEffectType.getByKey(NamespacedKey.fromString(args[1])))){
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-potion-block.not-added","&cThat potion is not blocked for that class.")));
            return;
        }
        clas.getBlockedPotions().remove(PotionEffectType.getByKey(NamespacedKey.fromString(args[1])));
        Main.CLASSES.update(clas.getUniqueId(),clas);

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.remove-potion-block.done","&aSuccessfully unblocked &b%potion% &afor &b%class%&a!")
                .replace("%potion%",args[1])
                .replace("%class%",clas.getName())
        ));
    }
}
