package dev.efekos.classes.commands.arguments;

import me.efekos.simpler.commands.node.ArgumentNode;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public final class DoubleArgumentNode extends ArgumentNode {
    @Override
    public List<String> suggest(CommandSender commandSender, List<String> list) {
        return new ArrayList<>();
    }

    @Override
    public boolean isCorrect(String s) {
        try {
            return Double.parseDouble(s) > Integer.MIN_VALUE;
        } catch (Exception e) {
            return false;
        }
    }
}
