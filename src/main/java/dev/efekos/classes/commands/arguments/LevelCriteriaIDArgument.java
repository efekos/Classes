package dev.efekos.classes.commands.arguments;

import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.argument.ArgumentRegistration;
import dev.efekos.arn.argument.CustomArgumentType;
import dev.efekos.arn.exception.ArnSyntaxException;
import dev.efekos.arn.exception.type.SimpleArnExceptionType;
import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.ILevelCriteria;
import dev.efekos.classes.api.registry.LevelCriteriaRegistry;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

@Container
public class LevelCriteriaIDArgument implements CustomArgumentType<ILevelCriteria> {

    public static final SimpleArnExceptionType<ArnSyntaxException> INVALID = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.invalid-id", "&cInvalid ID."))));
    public static final SimpleArnExceptionType<ArnSyntaxException> MOD_404 = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.cri-404", "&cThere isn't a criteria with that name."))));

    @Override
    public Class<ILevelCriteria> getType() {
        return ILevelCriteria.class;
    }

    @Override
    public List<String> suggest(CommandSender sender) {
        return Main.CRITERIA_REGISTRY.getAll().keySet().stream().map(NamespacedKey::toString).collect(Collectors.toList());
    }

    @Override
    public ArgumentRegistration getRegistration() {
        return ArgumentRegistration.ID;
    }

    @Override
    public ILevelCriteria parse(CommandSender sender, String arg) throws ArnSyntaxException {
        LevelCriteriaRegistry provider = Main.CRITERIA_REGISTRY;
        if (provider == null) return null;
        try {
            NamespacedKey.fromString(arg);
        } catch (Exception e) {
            throw INVALID.create();
        }
        ILevelCriteria modifier = provider.get(NamespacedKey.fromString(arg));
        if (modifier == null) throw MOD_404.create();
        return modifier;
    }
}