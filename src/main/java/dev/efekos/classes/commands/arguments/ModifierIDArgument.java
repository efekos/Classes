package dev.efekos.classes.commands.arguments;

import dev.efekos.arn.argument.CustomArgumentType;
import dev.efekos.arn.exception.ArnSyntaxException;
import dev.efekos.arn.exception.type.SimpleArnExceptionType;
import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.data.Class;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class ModifierIDArgument implements CustomArgumentType<IModifier> {

    @Override
    public java.lang.Class<IModifier> getType() {
        return IModifier.class;
    }

    @Override
    public List<String> suggest(CommandSender sender) {
        return Main.MODIFIER_REGISTRY.getAll().keySet().stream().map(NamespacedKey::toString).collect(Collectors.toList());
    }

    public static final SimpleArnExceptionType<ArnSyntaxException> INVALID = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.invalid-id", "&cInvalid ID."))));
    public static final SimpleArnExceptionType<ArnSyntaxException> MOD_404 = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.mod-404", "&cThere isn't a modifier with that name."))));

    @Override
    public IModifier parse(CommandSender sender, String arg) throws ArnSyntaxException {
        ModifierRegistry provider = Main.MODIFIER_REGISTRY;
        if(provider == null) return null;
        try {
            NamespacedKey.fromString(arg);
        } catch (Exception e) {
            throw INVALID.create();
        }
        IModifier modifier = provider.get(NamespacedKey.fromString(arg));
        if(modifier == null) throw MOD_404.create();
        return modifier;
    }
}