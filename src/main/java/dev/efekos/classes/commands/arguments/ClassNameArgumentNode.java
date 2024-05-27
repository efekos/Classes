package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import me.efekos.simpler.commands.node.ArgumentNode;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public final class ClassNameArgumentNode extends ArgumentNode {
    @Override
    public List<String> suggest(CommandSender commandSender, List<String> list) {
        return Main.CLASSES.getAll().stream().map(Class::getName).collect(Collectors.toList());
    }

    @Override
    public boolean isCorrect(String given) {
        return Main.getClassByName(given) != null;
    }
}
