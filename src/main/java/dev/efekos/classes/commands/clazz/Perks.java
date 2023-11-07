package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.api.registry.PerkRegistry;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.PerkApplier;
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

@Command(name = "perks",description = "List of all the perks in a class",permission = "classes.perks")
public final class Perks extends SubCommand {
    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class",ArgumentPriority.REQUIRED));
    }

    public Perks(@NotNull String name) {
        super(name);
    }

    public Perks(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        List<String> perkStrings = new ArrayList<>();
        for (PerkApplier applier : clas.getPerks()) {
            IPerk perk = PerkRegistry.getInstance().get(applier.getPerkId());
            if(perk==null)continue;
            perkStrings.add(ChatColor.AQUA+applier.getPerkId().toString() +"&6 - &e"+ perk.getDescription(ClassManager.getLevel(player.getUniqueId())));
        }

        for (String s : perkStrings) {
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
        List<String> perkStrings = new ArrayList<>();
        for (PerkApplier applier : clas.getPerks()) {
            IPerk perk = PerkRegistry.getInstance().get(applier.getPerkId());
            if(perk==null)continue;
            perkStrings.add(ChatColor.AQUA+applier.getPerkId().toString() +"&6 - &e"+ perk.getDescription(1));
        }

        for (String s : perkStrings) {
            sender.sendMessage(TranslateManager.translateColors(s));
        }
    }
}
