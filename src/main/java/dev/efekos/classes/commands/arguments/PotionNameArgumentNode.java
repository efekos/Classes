package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import me.efekos.simpler.commands.node.ArgumentNode;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PotionNameArgumentNode extends ArgumentNode {


    @Override
    public List<String> suggest(CommandSender player, List<String> args) {
        List<String> list = new ArrayList<>();

        for (PotionEffectType type : PotionEffectType.values()) {
            NamespacedKey key = type.getKey();

            list.add(key.getNamespace()+":"+key.getKey());
        }

        return list;
    }

    @Override
    public boolean isCorrect(String s) {
        return suggest(null,null).contains(s);
    }
}
