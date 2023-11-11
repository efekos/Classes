package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.registry.ModifierRegistry;
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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "join",description = "Join a class",playerOnly = true,permission = "classes.join")
public final class Join extends SubCommand {
    public Join(@NotNull String name) {
        super(name);
    }

    public Join(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class", ArgumentPriority.REQUIRED));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }

        if(ClassManager.hasClass(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.join.another", "&cYou are already in another class. Consider leaving it with &b/class leave &cbefore joining a new one.")));
            return;
        }

        ClassManager.setClass(player.getUniqueId(),clas);

        clas.getModifiers().forEach(modifierApplier -> {
            IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
            if(IModifier !=null) IModifier.apply(player,ClassManager.getLevel(player.getUniqueId()),modifierApplier.getValue());
        });
        for (PerkApplier perk : clas.getPerks()) {
            Main.PERK_REGISTRY.get(perk.getPerkId()).grant(player, ClassManager.getLevel(player.getUniqueId()));
        }

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.join.done","&aSuccessfully joined &b%class% &aclass! Check out your pros and cons by typing &b/class info&a.").replace("%class%",clas.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender consoleCommandSender, String[] strings) {

    }
}
