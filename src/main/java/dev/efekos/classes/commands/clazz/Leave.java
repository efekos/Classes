package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.PerkApplier;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "leave",description = "Leave your own class",permission = "classes.leave",playerOnly = true)
public final class Leave extends SubCommand {
    public Leave(@NotNull String name) {
        super(name);
    }

    public Leave(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] strings) {
        if(!ClassManager.hasClass(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.leave.not-class","&cYou are not in a class.")));
            return;
        }
        dev.efekos.classes.data.Class clas = ClassManager.getClass(player.getUniqueId());

        clas.getModifiers().forEach(modifierApplier -> {
            IModifier IModifier = ModifierRegistry.getInstance().get(modifierApplier.getModifierId());
            if(IModifier !=null) IModifier.deapply(player);
        });
        for (PerkApplier perk : clas.getPerks()) {
            PerkRegistry.getInstance().get(perk.getPerkId()).degrade(player);
        }

        ClassManager.removeClass(player.getUniqueId());

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.leave.done","&aSuccessfully left your class!")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender consoleCommandSender, String[] strings) {

    }
}
