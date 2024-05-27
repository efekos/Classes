package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import me.efekos.simpler.commands.node.ArgumentNode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public final class LevelCriteriaIDArgumentNode extends ArgumentNode {
    @Override
    public boolean isCorrect(String s) {
        try {
            LevelCriteriaRegistry provider = Main.CRITERIA_REGISTRY;
            assert provider != null;
            return provider.get(NamespacedKey.fromString(s)) != null;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public List<String> suggest(CommandSender commandSender, List<String> list) {
        return Main.CRITERIA_REGISTRY.getAll().keySet().stream().map(namespacedKey -> namespacedKey.getNamespace() + ":" + namespacedKey.getKey()).collect(Collectors.toList());
    }
}
