package dev.efekos.classes.commands.arguments;

import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.argument.ArgumentRegistration;
import dev.efekos.arn.argument.CustomArgumentType;
import dev.efekos.arn.exception.ArnSyntaxException;
import dev.efekos.arn.exception.type.SimpleArnExceptionType;
import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.api.registry.PerkRegistry;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

@Container
public class PerkIDArgument implements CustomArgumentType<IPerk> {

    public static final SimpleArnExceptionType<ArnSyntaxException> INVALID = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.invalid-id", "&cInvalid ID."))));
    public static final SimpleArnExceptionType<ArnSyntaxException> MOD_404 = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.prk-404", "&cThere isn't a perk with that name."))));

    @Override
    public java.lang.Class<IPerk> getType() {
        return IPerk.class;
    }

    @Override
    public List<String> suggest(CommandSender sender) {
        return Main.PERK_REGISTRY.getAll().keySet().stream().map(NamespacedKey::toString).collect(Collectors.toList());
    }

    @Override
    public ArgumentRegistration getRegistration() {
        return ArgumentRegistration.ID;
    }

    @Override
    public IPerk parse(CommandSender sender, String arg) throws ArnSyntaxException {
        PerkRegistry provider = Main.PERK_REGISTRY;
        if (provider == null) return null;
        try {
            NamespacedKey.fromString(arg);
        } catch (Exception e) {
            throw INVALID.create();
        }
        IPerk modifier = provider.get(NamespacedKey.fromString(arg));
        if (modifier == null) throw MOD_404.create();
        return modifier;
    }
}