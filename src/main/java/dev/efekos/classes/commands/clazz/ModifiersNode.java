package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import me.efekos.simpler.commands.CommandExecuteContext;
import me.efekos.simpler.commands.node.CommandExecutive;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public final class ModifiersNode implements CommandExecutive {
    @Override
    public void onExecute(CommandExecuteContext context) {
        List<String> args = context.args();
        CommandSender sender = context.sender();

        dev.efekos.classes.data.Class clas = Main.getClassByName(args.get(0));
        if (clas == null) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class", "&cThere is no class with that name.")));
            return;
        }
        List<String> modifierStrings = new ArrayList<>();
        clas.getModifiers().forEach(modifierApplier -> {
            IModifier IModifier = Main.MODIFIER_REGISTRY.get(modifierApplier.getModifierId());
            if (IModifier == null) return;

            modifierStrings.add(ChatColor.AQUA + modifierApplier.getModifierId().toString() + " &6 - &e" + IModifier.getDescription(1, modifierApplier.getValue()));
        });

        for (String s : modifierStrings) {
            sender.sendMessage(TranslateManager.translateColors(s));
        }
    }
}
