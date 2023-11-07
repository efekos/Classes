package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PotionNameArgument extends Argument {

    private final ArgumentPriority priority;
    private final String holder;

    public PotionNameArgument(ArgumentPriority priority, String holder) {
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

        for (PotionEffectType type : PotionEffectType.values()) {
            NamespacedKey key = type.getKey();

            list.add(key.getNamespace()+":"+key.getKey());
        }

        return list.stream().filter(s1 -> s1.startsWith(s)).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return priority;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String s) {
        try {
            Material.valueOf(s);
            return ArgumentHandleResult.success();
        } catch (Exception e){
            return ArgumentHandleResult.fail(Main.LANG.getString("args.potion.invalid","Invalid potion name"));
        }
    }
}
