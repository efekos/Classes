package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import dev.efekos.classes.commands.Class;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "delete",description = "Deletes an existing class",permission = "classes.delete")
public final class Delete extends SubCommand {
    public Delete(@NotNull String name) {
        super(name);
    }

    public Delete(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
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
        if (Main.CLASSES.getAll().stream().noneMatch(aClass -> aClass.getName().equals(args[0]))) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("delete.unexists", "&cA class called &b%name% &calready doesn't exists.").replace("%name%", args[0])));
            return;
        }

        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);

        ClassManager.getDatas().forEach((uuid, playerData) -> {
            if(playerData.getCurrentClass()!=null&&playerData.getCurrentClass().equals(clas.getUniqueId())){
                Player p = Bukkit.getPlayer(uuid);
                if(p==null)return;
                clas.getPerks().forEach(perkApplier -> Main.PERK_REGISTRY.get(perkApplier.getPerkId()).degrade(p));
                clas.getModifiers().forEach(modifierApplier -> Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId()).deapply(p));
                playerData.setCurrentClass(null);
                playerData.getClassLevels().remove(clas.getUniqueId());
                p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.notification","&eThe &b%class% &eclass you're in just got deleted by a server admin.")));
            }
        });

        Main.CLASSES.delete(clas.getUniqueId());

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.done","&aSuccessfully deleted the class &b%name%&a! Keep in mind that some users of this class might still have some of its modifiers activated until respawn.").replace("%name%",args[0])));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        if (Main.CLASSES.getAll().stream().noneMatch(aClass -> aClass.getName().equals(args[0]))) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("delete.unexists", "&cA class called &b%name% &calready doesn't exists.").replace("%name%", args[0])));
            return;
        }

        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);

        ClassManager.getDatas().forEach((uuid, playerData) -> {
            if(playerData.getCurrentClass().equals(clas.getUniqueId())){
                Player p = Bukkit.getPlayer(uuid);
                if(p==null)return;
                clas.getPerks().forEach(perkApplier -> Main.PERK_REGISTRY.get(perkApplier.getPerkId()).degrade(p));
                clas.getModifiers().forEach(modifierApplier -> Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId()).deapply(p));
                playerData.setCurrentClass(null);
                playerData.getClassLevels().remove(clas.getUniqueId());
                p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.notification","&eThe &b%class% &eclass you're in just got deleted by a server admin.")));
            }
        });

        Main.CLASSES.delete(clas.getUniqueId());

        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.delete.done","&aSuccessfully deleted the class &b%name%&a! Keep in mind that some users of this class might still have some of its modifiers activated until respawn.").replace("%name%",args[0])));
    }
}
