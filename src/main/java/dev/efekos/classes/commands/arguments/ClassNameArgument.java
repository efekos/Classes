package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class ClassNameArgument extends Argument {
    private final String holder;
    private final ArgumentPriority priority;

    public ClassNameArgument(String holder, ArgumentPriority priority) {
        this.holder = holder;
        this.priority = priority;
    }

    @Override
    public String getPlaceHolder() {
        return holder;
    }

    @Override
    public List<String> getList(Player player, String s) {
        return Main.CLASSES.getAll().stream().map(Class::getName).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return priority;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String given) {
        if(Main.getClassByName(given)==null) return ArgumentHandleResult.fail(Main.LANG.getString("args.class_name.invalid","There is no class with the name: %name%").replace("%name%",given));
        return ArgumentHandleResult.success();
    }
}
