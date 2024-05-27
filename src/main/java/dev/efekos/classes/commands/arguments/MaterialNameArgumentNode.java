package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Utilities;
import me.efekos.simpler.commands.node.ArgumentNode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public final class MaterialNameArgumentNode extends ArgumentNode {

    @Override
    public List<String> suggest(CommandSender commandSender, List<String> l) {
        List<String> list = new ArrayList<>();

        for (Material material : Material.values()) {
            NamespacedKey key = material.getKey();

            list.add(key.getNamespace() + ":" + key.getKey());
        }

        return list;
    }

    @Override
    public boolean isCorrect(String s) {
        return Utilities.getMaterialByKey(s) != null;
    }
}
