package dev.efekos.classes.commands;

import dev.efekos.classes.commands.clazz.*;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@Command(name = "class",description = "Commands of class")
public final class Class extends CoreCommand {
    @Override
    public @NotNull List<java.lang.Class<? extends SubCommand>> getSubs() {
        return Arrays.asList(
                Create.class, Join.class,Leave.class, Delete.class,
                AddMaterialBlock.class, AddEnchantmentBlock.class, AddPotionBlock.class,
                RemoveMaterialBlock.class, RemoveEnchantmentBlock.class, RemovePotionBlock.class,
                AddModifier.class, RemoveModifier.class, AddPerk.class, RemovePerk.class,
                Perks.class, Modifiers.class, SetCriteria.class, SetDescription.class, Info.class,Kit.class,SetIcon.class, Members.class
        );
    }

    public Class(@NotNull String name) {
        super(name);
    }

    public Class(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void renderHelpList(CommandSender commandSender, List<SubCommand> list) {
        for (SubCommand command : list) {
            commandSender.sendMessage(ChatColor.AQUA + command.getUsage() + ChatColor.GOLD + " - " + ChatColor.YELLOW + command.getDescription());
        }
    }
}
