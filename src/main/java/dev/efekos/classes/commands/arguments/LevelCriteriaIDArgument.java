package dev.efekos.classes.commands.arguments;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.List;
import java.util.stream.Collectors;

public final class LevelCriteriaIDArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "criteria";
    }

    @Override
    public List<String> getList(Player player, String s) {
        return Main.CRITERIA_REGISTRY.getAll().keySet().stream().map(namespacedKey -> namespacedKey.getNamespace()+":"+namespacedKey.getKey()).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String s) {
        try {
            LevelCriteriaRegistry provider = Main.CRITERIA_REGISTRY;
            assert provider != null;
            if(provider.get(NamespacedKey.fromString(s))!=null)return ArgumentHandleResult.success();
            else return ArgumentHandleResult.fail(Main.LANG.getString("args.level_criteria.invalid","Invalid level criteria id"));
        } catch (Exception ignored){
            return ArgumentHandleResult.fail(Main.LANG.getString("args.level_criteria.invalid","Invalid level criteria id"));
        }
    }
}
