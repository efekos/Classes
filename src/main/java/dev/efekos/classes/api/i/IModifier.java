package dev.efekos.classes.api.i;

import org.bukkit.entity.Player;

public interface IModifier {
    void apply(Player player,int level,double value);
    void deapply(Player player);

    /**
     * Returns a description about this modifier, that must follow the rules below.
     * <ul>
     *     <li>Description must have no color codes.</li>
     * </ul>
     * @return A description of this modifier.
     */
    String getDescription(int level,double value);

    boolean isPositive(int level,double value);
}
