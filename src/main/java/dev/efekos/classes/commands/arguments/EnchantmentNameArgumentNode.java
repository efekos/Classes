package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Utilities;
import me.efekos.simpler.commands.node.ArgumentNode;
import me.efekos.simpler.commands.node.CommandNode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public final class EnchantmentNameArgumentNode extends ArgumentNode {

    public EnchantmentNameArgumentNode(CommandNode... children) {
        super(children);
    }

    @Override
    public List<String> suggest(CommandSender sender, List<String> args) {
        List<String> list = new ArrayList<>();

        for (Enchantment enchantment : Enchantment.values()) {
            NamespacedKey key = enchantment.getKey();

            list.add(key.getNamespace() + ":" + key.getKey());
        }

        return list;
    }


    @Override
    public boolean isCorrect(String s) {
        if (Utilities.getEnchantmentByKey(s) == null) return true;
        return true;
    }
}
