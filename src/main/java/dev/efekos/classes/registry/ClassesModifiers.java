package dev.efekos.classes.registry;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.events.PlayerEvents;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class ClassesModifiers {
    private static <T extends IModifier> T register(String name, T modifier){
        return Main.MODIFIER_REGISTRY.register(new NamespacedKey(Main.getInstance(),name),modifier);
    }
    public static final AttributeModifier MOVEMENT_SPEED = register("movement_speed", new AttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,0.1,Main.LANG.getString("modifiers.movement_speed","Run %p% faster."),0.005));
    public static final MaxHealthModifier MAX_HEALTH = register("max_health",new MaxHealthModifier());
    public static final AttributeModifier LUCK = register("luck",new AttributeModifier(Attribute.GENERIC_LUCK,0,Main.LANG.getString("modifiers.luck","Have %a% luck points."),0.01));
    public static final AttributeModifier ATTACK_SPEED = register("attack_speed",new AttributeModifier(Attribute.GENERIC_ATTACK_SPEED,4,Main.LANG.getString("modifiers.attack_speed","Increase attack speed by %p%."),0.05));
    public static final AttributeModifier ATTACK_DAMAGE = register("attack_damage",new AttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,2,Main.LANG.getString("modifiers.attack_damage","Increase attack damage by %p%."),0.05));
    public static final AttributeModifier ARMOR_POINTS = register("armor_points",new AttributeModifier(Attribute.GENERIC_ARMOR,0,Main.LANG.getString("modifiers.armor_points","Have %a% armor points by default, even when you don't have any armor."),0.5));
    public static final AttributeModifier ARMOR_TOUGHNESS = register("armor_toughness",new AttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,0,Main.LANG.getString("modifiers.armor_toughness","Have %a% armor toughness by default."),0.5));
    public static final AttributeModifier KNOCKBACK_RESISTANCE = register("knockback_resistance",new AttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,0,Main.LANG.getString("modifiers.knockback_resistance","Resist %a% more to knockbacks."),0.1));
    public static final IModifier BOW_ACCURACY = register("bow_accuracy", new IModifier() {
        @Override
        public void apply(Player player, int level, double value) {
            PlayerEvents.playersToArrowBoost.put(player.getUniqueId(),value+Math.floor(value*Math.floor((double) level /8)));
        }

        @Override
        public boolean isPositive(int level, double value) {
            return true;
        }

        @Override
        public void deapply(Player player) {
            PlayerEvents.playersToArrowBoost.remove(player.getUniqueId());
        }

        @Override
        public String getDescription(int level,double value) {
            return Main.LANG.getString("modifiers.bow_accuracy.desc","Arrows go the same way for %a% seconds")
                    .replace("%a%", Utilities.generateAmountText((int) (value+Math.floor(value*Math.floor((double) level /8))), ChatColor.YELLOW+""));
        }
    });
}
