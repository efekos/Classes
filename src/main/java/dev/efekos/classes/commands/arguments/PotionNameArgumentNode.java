package dev.efekos.classes.commands.arguments;

import me.efekos.simpler.commands.node.ArgumentNode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class PotionNameArgumentNode extends ArgumentNode {


    @Override
    public List<String> suggest(CommandSender player, List<String> args) {
        List<String> list = new ArrayList<>();

        for (PotionEffectType type : PotionEffectType.values()) {
            NamespacedKey key = type.getKey();

            list.add(key.getNamespace() + ":" + key.getKey());
        }

        return list;
    }

    @Override
    public boolean isCorrect(String s) {
        return suggest(null, null).contains(s);
    }
}
