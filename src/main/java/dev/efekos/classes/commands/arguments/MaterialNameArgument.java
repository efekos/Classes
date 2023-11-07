package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MaterialNameArgument extends Argument {

    private final ArgumentPriority priority;
    private final String holder;

    public MaterialNameArgument(ArgumentPriority priority, String holder) {
        this.priority = priority;
        this.holder = holder;
    }

    @Override
    public String getPlaceHolder() {
        return holder;
    }

    @Override
    public List<String> getList(Player player, String s) {
        List<String> list = new ArrayList<>();

        for (Material material : Material.values()) {
            NamespacedKey key = material.getKey();

            list.add(key.getNamespace()+":"+key.getKey());
        }

        return list.stream().filter(s1 -> s1.contains(s)).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return priority;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String s) {
        if(Utilities.getMaterialByKey(s)==null) return ArgumentHandleResult.fail(Main.LANG.getString("args.item.invalid","Invalid material name"));
        return ArgumentHandleResult.success();
    }
}
