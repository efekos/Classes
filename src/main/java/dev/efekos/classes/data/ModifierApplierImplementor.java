package dev.efekos.classes.data;

import dev.efekos.simple_ql.data.GetterAction;
import dev.efekos.simple_ql.data.SetterAction;
import dev.efekos.simple_ql.implementor.Implementor;
import org.bukkit.NamespacedKey;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class ModifierApplierImplementor implements Implementor<ModifierApplier,String> {

    public static final ModifierApplierImplementor INSTANCE = new ModifierApplierImplementor();

    @Override
    public String write(ModifierApplier modifierApplier) {
        return modifierApplier.getModifierId().toString()+"|"+modifierApplier.getValue();
    }

    @Override
    public ModifierApplier read(String s) {
        String[] ss = s.split("\\|");
        return new ModifierApplier(Objects.requireNonNull(NamespacedKey.fromString(ss[0])),Double.parseDouble(ss[1]));
    }

    @Override
    public SetterAction<String> setter() {
        return PreparedStatement::setString;
    }

    @Override
    public GetterAction<String> getter() {
        return ResultSet::getString;
    }

    @Override
    public String type() {
        return "TEXT";
    }
}
