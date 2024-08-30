package dev.efekos.classes.registry;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.ILevelCriteria;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Arrays;
import java.util.List;

public final class ClassesCriterias {
    public static final ILevelCriteria TAKE_DAMAGE = register("take_damage", event -> {
        if (event instanceof EntityDamageEvent e) return e.getEntity() instanceof Player;
        else return false;
    });
    public static final ILevelCriteria DEAL_DAMAGE = register("deal_damage", event -> event instanceof EntityDamageByEntityEvent e && e.getDamager() instanceof Player);
    public static final ILevelCriteria SHOOT_ARROW = register("shoot_arrow", event -> event instanceof ProjectileLaunchEvent e && e.getEntity() instanceof Arrow);
    public static final ILevelCriteria SHOOT_SNOWBALL = register("shoot_snowball", event -> event instanceof ProjectileLaunchEvent e && e.getEntity() instanceof Snowball);
    public static final ILevelCriteria SHOOT_EGG = register("shoot_egg", event -> event instanceof ProjectileLaunchEvent e && e.getEntity() instanceof Egg);
    public static final ILevelCriteria SHOOT_ENDERPEARL = register("shoot_enderpearl", event -> event instanceof ProjectileLaunchEvent e && e.getEntity() instanceof EnderPearl);
    public static final ILevelCriteria HIT_ARROW = register("hit_arrow", event -> event instanceof ProjectileHitEvent e && e.getEntity() instanceof Arrow);
    public static final ILevelCriteria HIT_SNOWBALL = register("hit_snowball", event -> event instanceof ProjectileHitEvent e && e.getEntity() instanceof Snowball);
    public static final ILevelCriteria HIT_ENDERPEARL = register("hit_enderpearl", event -> event instanceof ProjectileHitEvent e && e.getEntity() instanceof EnderPearl);
    public static final ILevelCriteria HIT_EGG = register("hit_egg", event -> event instanceof ProjectileHitEvent e && e.getEntity() instanceof Egg);
    public static final ILevelCriteria EAT_FOOD = register("eat_food", event -> event instanceof PlayerItemConsumeEvent);
    public static final ILevelCriteria KILL_PLAYER = register("kill_player", event -> {
        if (event instanceof PlayerDeathEvent e) return e.getEntity().getKiller() != null;
        return false;
    });
    public static final ILevelCriteria KILL_ENTITY = register("kill_entity", event -> {
        if (event instanceof EntityDeathEvent e) return e.getEntity().getKiller() != null;
        return false;
    });
    public static final ILevelCriteria MINE_BLOCK = register("mine_block", event -> event instanceof BlockBreakEvent e && e.getBlock().getMetadata("placenByPlayer").isEmpty());
    public static final ILevelCriteria TAKE_FALL_DAMAGE = register("take_fall_damage", createTakeDamage(EntityDamageEvent.DamageCause.FALL));
    public static final ILevelCriteria TAKE_DROWNING_DAMAGE = register("take_drowning_damage", createTakeDamage(EntityDamageEvent.DamageCause.DROWNING));
    public static final ILevelCriteria TAKE_WITHER_DAMAGE = register("take_wither_damage", createTakeDamage(EntityDamageEvent.DamageCause.WITHER));
    public static final ILevelCriteria TAKE_LAVA_DAMAGE = register("take_lava_damage", createTakeDamage(EntityDamageEvent.DamageCause.LAVA));
    public static final ILevelCriteria TAKE_FIRE_DAMAGE = register("take_fire_damage", createTakeDamage(EntityDamageEvent.DamageCause.FIRE));
    public static final ILevelCriteria TAKE_STARVE_DAMAGE = register("take_starve_damage", createTakeDamage(EntityDamageEvent.DamageCause.STARVATION));
    public static final ILevelCriteria TAKE_LIGHTNING_DAMAGE = register("take_lightning_damage", createTakeDamage(EntityDamageEvent.DamageCause.LIGHTNING));
    public static final ILevelCriteria TAKE_FALLING_BLOCK_DAMAGE = register("take_falling_block_damage", createTakeDamage(EntityDamageEvent.DamageCause.FALLING_BLOCK));
    public static final ILevelCriteria TAKE_FREEZING_DAMAGE = register("take_freezing_damage", createTakeDamage(EntityDamageEvent.DamageCause.FREEZE));
    public static final ILevelCriteria TAKE_POISON_DAMAGE = register("take_poison_damage", createTakeDamage(EntityDamageEvent.DamageCause.POISON));
    public static final ILevelCriteria CRAFT = register("craft", event -> event instanceof CraftItemEvent);
    public static final List<Material> tools = Arrays.asList(
            Material.WOODEN_AXE, Material.WOODEN_HOE, Material.WOODEN_PICKAXE, Material.WOODEN_SHOVEL, Material.WOODEN_SWORD,
            Material.STONE_AXE, Material.STONE_HOE, Material.STONE_PICKAXE, Material.STONE_SHOVEL, Material.STONE_SWORD,
            Material.GOLDEN_AXE, Material.GOLDEN_HOE, Material.GOLDEN_PICKAXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_SWORD,
            Material.IRON_AXE, Material.IRON_HOE, Material.IRON_PICKAXE, Material.IRON_SHOVEL, Material.IRON_SWORD,
            Material.DIAMOND_AXE, Material.DIAMOND_HOE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SHOVEL, Material.DIAMOND_SWORD,
            Material.NETHERITE_AXE, Material.NETHERITE_HOE, Material.NETHERITE_PICKAXE, Material.NETHERITE_SHOVEL, Material.NETHERITE_SWORD
    );
    public static final List<Material> armor = Arrays.asList(
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
            Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS,
            Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS
    );
    public static final List<Material> stations = Arrays.asList(
            Material.CRAFTING_TABLE, Material.CARTOGRAPHY_TABLE, Material.ENCHANTING_TABLE, Material.FLETCHING_TABLE, Material.SMITHING_TABLE,
            Material.FURNACE, Material.BLAST_FURNACE
    );
    public static final ILevelCriteria CRAFT_TOOL = register("craft_tool", event -> event instanceof CraftItemEvent e && tools.contains(e.getRecipe().getResult().getType()));
    public static final ILevelCriteria CRAFT_ARMOR = register("craft_armor", event -> event instanceof CraftItemEvent e && armor.contains(e.getRecipe().getResult().getType()));
    public static final ILevelCriteria CRAFT_STATION = register("craft_station", event -> event instanceof CraftItemEvent e && stations.contains(e.getRecipe().getResult().getType()));
    private static final List<Material> ores = Arrays.asList(Material.COAL_ORE, Material.COPPER_ORE, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_COPPER_ORE,
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_IRON_ORE, Material.IRON_ORE, Material.DEEPSLATE_LAPIS_ORE, Material.LAPIS_ORE);
    public static final ILevelCriteria MINE_ORE = register("mine_ore", event -> {
        if (!(event instanceof BlockBreakEvent e)) return false;
        Block b = e.getBlock();

        if (b.getMetadata("placenByPlayer").isEmpty()) return ores.contains(b.getType());
        return false;
    });
    private static final List<Material> logs = Arrays.asList(Material.ACACIA_LOG, Material.BIRCH_LOG, Material.CHERRY_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG,
            Material.MANGROVE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG);
    public static final ILevelCriteria MINE_LOG = register("mine_log", event -> {
        if (!(event instanceof BlockBreakEvent e)) return false;
        Block b = e.getBlock();

        if (b.getMetadata("placenByPlayer").isEmpty()) return logs.contains(b.getType());
        return false;
    });

    private static <T extends ILevelCriteria> T register(String name, T modifier) {
        return Main.CRITERIA_REGISTRY.register(new NamespacedKey(Main.getInstance(), name), modifier);
    }

    private static ILevelCriteria createTakeDamage(EntityDamageEvent.DamageCause cause) {
        return event -> event instanceof EntityDamageEvent e && e.getEntity() instanceof Player && e.getCause() == cause;
    }
}
