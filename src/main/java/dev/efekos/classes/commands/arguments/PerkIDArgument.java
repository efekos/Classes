package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.registry.PerkRegistry;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.List;
import java.util.stream.Collectors;

public final class PerkIDArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "modifier";
    }

    @Override
    public List<String> getList(Player player, String s) {
        return Main.PERK_REGISTRY.getAll().keySet().stream().map(namespacedKey -> namespacedKey.getNamespace()+":"+namespacedKey.getKey()).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String s) {
        try {
            PerkRegistry provider = Main.PERK_REGISTRY;
            assert provider != null;
            if(provider.get(NamespacedKey.fromString(s))!=null)return ArgumentHandleResult.success();
            else return ArgumentHandleResult.fail(Main.LANG.getString("args.perk.invalid","Invalid perk id"));
        } catch (Exception ignored){
            return ArgumentHandleResult.fail(Main.LANG.getString("args.perk.invalid","Invalid perk id"));
        }
    }
}
