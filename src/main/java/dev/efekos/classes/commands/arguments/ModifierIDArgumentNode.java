package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.registry.ModifierRegistry;
import me.efekos.simpler.commands.node.ArgumentNode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public final class ModifierIDArgumentNode extends ArgumentNode {
    @Override
    public boolean isCorrect(String s) {
        try {
            ModifierRegistry provider = Main.MODIFIER_REGISTRY;
            assert provider != null;
            return provider.get(NamespacedKey.fromString(s)) != null;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public List<String> suggest(CommandSender commandSender, List<String> list) {
        return Main.MODIFIER_REGISTRY.getAll().keySet().stream().map(namespacedKey -> namespacedKey.getNamespace() + ":" + namespacedKey.getKey()).collect(Collectors.toList());
    }
}
