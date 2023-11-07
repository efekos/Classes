package dev.efekos.classes.commands.arguments;

import me.efekos.simpler.Simpler;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.entity.Player;

import java.util.List;

public final class DoubleArgument extends Argument {
    private final String holder;
    private final ArgumentPriority priority;

    public DoubleArgument(String holder, ArgumentPriority priority) {
        this.holder = holder;
        this.priority = priority;
    }

    @Override
    public String getPlaceHolder() {
        return holder;
    }

    @Override
    public List<String> getList(Player player, String s) {
        return null;
    }

    @Override
    public ArgumentPriority getPriority() {
        return priority;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String s) {
        try {
            if(Double.parseDouble(s)>Integer.MIN_VALUE) return ArgumentHandleResult.success();
            else return ArgumentHandleResult.fail(Simpler.getMessageConfiguration().NUM_ARG_NAN.replace("%given%",s));
        } catch (Exception e) {
            return ArgumentHandleResult.fail(Simpler.getMessageConfiguration().NUM_ARG_NAN.replace("%given%", s));
        }
    }
}
