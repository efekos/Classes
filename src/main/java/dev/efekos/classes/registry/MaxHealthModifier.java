package dev.efekos.classes.registry;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IModifier;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class MaxHealthModifier implements IModifier {
    @Override
    public void apply(Player player, int level, double value) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
    }

    @Override
    public boolean isPositive(int level, double value) {
        return calculatePercentage(level,value)-100>=0;
    }

    @Override
    public void deapply(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }

    @Override
    public String getDescription(int level,double value) {
        return Main.LANG.getString("modifiers.max_health.desc","Have %p% more health &5(&dmeans %a%&d hearts&5)&e.")
                .replace("%p%",Utilities.generatePercentageText(calculatePercentage(level,value), ChatColor.YELLOW+""))
                .replace("%a%",Utilities.generateAmountText(calculateAmount(level,value),ChatColor.YELLOW+""));
    }

    private int calculatePercentage(int level, double value) {

        return (int) ((value/20.0)*100);
    }

    public int calculateAmount(int level, double value) {
        return (int) (value/2);
    }
}
