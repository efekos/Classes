package dev.efekos.classes.commands.arguments;

import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.node.ArgumentNode;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            if(Double.parseDouble(s)>Integer.MIN_VALUE) return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }
}
