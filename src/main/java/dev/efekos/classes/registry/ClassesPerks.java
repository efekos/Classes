package dev.efekos.classes.registry;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.events.HandlingEvents;
import dev.efekos.classes.registry.perk.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class ClassesPerks {
    private static <T extends IPerk> T register(String name, T modifier) {
        return Main.PERK_REGISTRY.register(new NamespacedKey(Main.getInstance(), name), modifier);
    }

    public static final IPerk FAST_ARROW = register("fast_arrow", new MapManagedPerk(level -> Main.LANG.getString("perks.fast_arrow", "Shoot arrows &a+%100&e faster"),() -> HandlingEvents.playersToBoostArrow));
    public static final IPerk FAST_PEARL = register("fast_pearl", new MapManagedPerk(level -> Main.LANG.getString("perks.fast_pearl", "Shoot ender pearls &a+%100&e faster"),() -> HandlingEvents.playersToBoostPearl));
    public static final IPerk FAST_SNOWBALL = register("fast_snowball", new MapManagedPerk(level -> Main.LANG.getString("perks.fast_snowball", "Shoot snowballs &a+%100&e faster"),() -> HandlingEvents.playersToBoostSnowball));
    public static final LifeStealerPerk LIFE_STEALER = register("life_stealer", new LifeStealerPerk());
    public static final AffecterEffectPerk FROSTER = register("froster", new AffecterEffectPerk((entity, level) -> {
        int bound = 5;
        bound += (level / 5) * 3;

        entity.setFreezeTicks(entity.getFreezeTicks() + bound);
    },level -> Main.LANG.getString("perks.froster", "&eHitting an enemy freezes it.")));
    public static final AffecterEffectPerk BURNER = register("burner", new AffecterEffectPerk((entity, level) -> {
        int bound = 5;
        bound += (level / 5) * 3;

        entity.setFireTicks(entity.getFireTicks() + bound);
    },level -> Main.LANG.getString("perks.burner", "&eHitting an enemy burns it.")));
    public static final AffecterEffectPerk POISONER = register("poisoner", new AffecterEffectPerk((entity, level) -> {
        if(!(entity instanceof LivingEntity e))return;
        int bound = 5;
        bound += (level / 5) * 3;

        e.addPotionEffect(new PotionEffect(PotionEffectType.POISON,bound,Math.min(30,level)/3));
    },level -> Main.LANG.getString("perks.poisoner", "&eHitting an enemy poisons it.")));

    public static final IPerk LAST_RUN = register("last_run", new HandledPerk(level -> Main.LANG.getString("perks.last_run", "Run faster when under &9%25&e health.")));
    public static final IPerk LAST_ATTACK = register("last_attack", new HandledPerk(level -> Main.LANG.getString("perks.last_attack", "Deal more damage when under &9%25&e health.")));
    public static final IPerk LAST_SHIELD = register("last_shield", new HandledPerk(level -> Main.LANG.getString("perks.last_shield", "Get Resistance effect when under &9%25&e health.")));

    public static final AffecterEffectPerk LOVE = register("love", new AffecterEffectPerk((entity, level) -> {
        if (!(entity instanceof LivingEntity e)) return;
        int a = 1+(level/10);
        e.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, a * 100, a, false, false, false));

    },level -> Main.LANG.getString("perks.love", "Killing an entity gives Strength %a% effect. ")
            .replace("%a%", Utilities.generateAmountText(1+(level/10), ChatColor.YELLOW + ""))));

    public static final AuraPerk AURA = register("aura", new AuraPerk(Main.LANG.getString("perks.aura", "Blocks within a distance of %a% blocks get on fire out of nowhere")) {
        @Override
        public void affectBlock(World world, Block block) {
            Location location = block.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            if (!world.getBlockAt(x, y, z).getType().isAir()) {
                if (world.getBlockAt(x + 1, y, z).getType().isAir())
                    world.getBlockAt(x + 1, y, z).setType(Material.FIRE, true);
                if (world.getBlockAt(x - 1, y, z).getType().isAir())
                    world.getBlockAt(x - 1, y, z).setType(Material.FIRE, true);
                if (world.getBlockAt(x, y + 1, z).getType().isAir())
                    world.getBlockAt(x, y + 1, z).setType(Material.FIRE, true);
                if (world.getBlockAt(x, y - 1, z).getType().isAir())
                    world.getBlockAt(x, y - 1, z).setType(Material.FIRE, true);
                if (world.getBlockAt(x, y, z + 1).getType().isAir())
                    world.getBlockAt(x, y, z + 1).setType(Material.FIRE, true);
                if (world.getBlockAt(x, y, z - 1).getType().isAir())
                    world.getBlockAt(x, y, z - 1).setType(Material.FIRE, true);
            }

        }
    });
}
