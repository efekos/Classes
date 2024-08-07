package dev.efekos.classes.commands.arguments;

import dev.efekos.arn.annotation.Container;
import dev.efekos.arn.annotation.CustomArgument;
import dev.efekos.arn.argument.ArgumentRegistration;
import dev.efekos.arn.argument.CustomArgumentType;
import dev.efekos.arn.exception.ArnSyntaxException;
import dev.efekos.arn.exception.type.SimpleArnExceptionType;
import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.CommandSender;

import java.util.List;

@Container
public class ClassArgument implements CustomArgumentType<Class> {

    @Override
    public java.lang.Class<Class> getType() {
        return dev.efekos.classes.data.Class.class;
    }

    @Override
    public ArgumentRegistration getRegistration() {
        return ArgumentRegistration.WORD;
    }

    @Override
    public List<String> suggest(CommandSender sender) {
        return Main.CLASSES.getAll().stream().map(Class::getName).toList();
    }

    public static final SimpleArnExceptionType<ArnSyntaxException> INVALID = new SimpleArnExceptionType<>(() -> new ArnSyntaxException(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class", "&cThere is no class with that name."))));

    @Override
    public Class parse(CommandSender sender, String arg) throws ArnSyntaxException {
        dev.efekos.classes.data.Class clas = Main.getClassByName(arg);
        if (clas == null) throw INVALID.create();
        return clas;
    }
}