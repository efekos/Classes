package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class EnchantmentNameArgument extends Argument {

    private final ArgumentPriority priority;
    private final String holder;

    public EnchantmentNameArgument(ArgumentPriority priority, String holder) {
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

        for (Enchantment enchantment : Enchantment.values()) {
            NamespacedKey key = enchantment.getKey();

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
            return ArgumentHandleResult.fail(Main.LANG.getString("args.enchantment.invalid","Invalid enchantment"));
        }
    }
}
