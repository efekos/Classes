package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import me.efekos.simpler.commands.node.ArgumentNode;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MaterialNameArgumentNode extends ArgumentNode {

    @Override
    public List<String> suggest(CommandSender commandSender, List<String> l) {
        List<String> list = new ArrayList<>();

        for (Material material : Material.values()) {
            NamespacedKey key = material.getKey();

            list.add(key.getNamespace()+":"+key.getKey());
        }

        return list;
    }

    @Override
    public boolean isCorrect(String s) {
        if(Utilities.getMaterialByKey(s)==null) return false;
        return true;
    }
}
