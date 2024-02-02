package dev.efekos.classes.registry;

import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IModifier;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public final class AttributeModifier implements IModifier {
    private final Attribute attribute;
    private final double def;
    private final String description;

    private final double additionPerTenLevels;

    public AttributeModifier(Attribute attribute, double def, String description, double additionPerTenLevels) {
        this.attribute = attribute;
        this.def = def;
        this.description = description;
        this.additionPerTenLevels = additionPerTenLevels;
    }

    public AttributeModifier(Attribute attribute, double def, String description) {
        this.attribute = attribute;
        this.def = def;
        this.description = description;
        additionPerTenLevels = 0;
    }

    public AttributeModifier(Attribute attribute, double def) {
        this.attribute = attribute;
        this.def = def;
        this.description = "";
        additionPerTenLevels = 0;
    }


    public double getAdditionPerTenLevels() {
        return additionPerTenLevels;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public String getDescription(int level,double value) {
        return description
                .replace("%p%", Utilities.generatePercentageText(calculatePercentage(level,value), ChatColor.YELLOW+""))
                .replace("%a%", Utilities.generateAmountText(calculateAmount(level,value), ChatColor.YELLOW+""));
    }

    public double getDef() {
        return def;
    }

    @Override
    public void apply(Player player, int level, double value) {
        double addition = 0.0;
        for (int i = 10; i <= 100; i += 10) {
            if (level >= i) {
                addition += additionPerTenLevels;
            }
        }


        player.getAttribute(attribute).setBaseValue(value+addition);
    }

    private double realValue(int level,double value){
        double addition = 0.0;
        for (int i = 10; i <= 100; i += 10) {
            if (level >= i) {
                addition += additionPerTenLevels;
            }
        }
        return value+addition;
    }

    public int calculatePercentage(int level, double value) {
        return (int) Math.round((realValue(level,value)/def)*100);
    }

    @Override
    public boolean isPositive(int level, double value) {
        return realValue(level,value)>=def;
    }

    public int calculateAmount(int level, double value) {
        double addition = 0.0;
        for (int i = 10; i <= 100; i += 10) {
            if (level >= i) {
                addition += additionPerTenLevels;
            }
        }
        return (int) (value+addition);
    }

    @Override
    public void deapply(Player player) {
        player.getAttribute(attribute).setBaseValue(def);
    }

    @Override
    public String toString() {
        return "AttributeModifier[attribute="+attribute.toString()+",def="+def+"]";
    }
}
