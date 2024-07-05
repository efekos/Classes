package dev.efekos.classes.registry;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.events.HandlingEvents;
import dev.efekos.classes.registry.perk.AuraPerk;
import dev.efekos.classes.registry.perk.LifeStealerPerk;
import dev.efekos.classes.registry.perk.TimedEffectPerk;
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

    public static final IPerk FAST_ARROW = register("fast_arrow", new IPerk() {
        @Override
        public void grant(Player player, int level) {
            HandlingEvents.playersToBoostArrow.put(player, level);
        }

        @Override
        public void degrade(Player player) {
            HandlingEvents.playersToBoostArrow.remove(player);
        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.fast_arrow", "Shoot arrows &a+%100&e faster");
        }
    });
    public static final IPerk FAST_PEARL = register("fast_pearl", new IPerk() {
        @Override
        public void grant(Player player, int level) {
            HandlingEvents.playersToBoostPearl.put(player, level);
        }

        @Override
        public void degrade(Player player) {
            HandlingEvents.playersToBoostPearl.remove(player);
        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.fast_pearl", "Shoot ender pearls &a+%100&e faster");
        }
    });
    public static final IPerk FAST_SNOWBALL = register("fast_snowball", new IPerk() {
        @Override
        public void grant(Player player, int level) {
            HandlingEvents.playersToBoostSnowball.put(player, level);
        }

        @Override
        public void degrade(Player player) {
            HandlingEvents.playersToBoostSnowball.remove(player);
        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.fast_snowball", "Shoot snowballs &a+%100&e faster");
        }
    });
    public static final LifeStealerPerk LIFE_STEALER = register("life_stealer", new LifeStealerPerk());
    public static final TimedEffectPerk BURNER = register("burner", new TimedEffectPerk() {
        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public void affectEntity(Entity entity, int level) {
            int bound = 5;
            bound += (level / 5) * 3;

            entity.setFireTicks(entity.getFireTicks() + bound);
        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.burner", "&eHitting an enemy burns it.");
        }
    });
    public static final TimedEffectPerk FROSTER = register("froster", new TimedEffectPerk() {
        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public void affectEntity(Entity entity, int level) {
            int bound = 5;
            bound += (level / 5) * 3;

            entity.setFireTicks(entity.getFireTicks() + bound);
        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.froster", "&eHitting an enemy freezes it.");
        }
    });
    public static final TimedEffectPerk POISONER = register("poisoner", new TimedEffectPerk() {
        @Override
        public void affectEntity(Entity entity, int level) {
            if (!(entity instanceof LivingEntity e)) return;

            int bound = 5;
            bound += (level / 5) * 3;

            int l = 0;
            if (level >= 5) l++;
            if (level >= 10) l++;
            if (level >= 15) l++;
            if (level >= 20) l++;
            if (level >= 25) l++;
            if (level >= 30) l++;
            if (level >= 35) l++;
            if (level >= 40) l++;
            if (level >= 45) l++;
            if (level >= 50) l++;
            if (level >= 55) l++;
            if (level >= 60) l++;
            if (level >= 65) l++;
            if (level >= 70) l++;
            if (level >= 75) l++;
            if (level >= 80) l++;
            if (level >= 85) l++;
            if (level >= 90) l++;
            if (level >= 95) l++;
            if (level >= 100) l++;

            e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, bound, l));
        }

        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.poisoner", "Hitting an entity poisons it.");
        }
    });
    public static final IPerk LAST_RUN = register("last_run", new IPerk() {
        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.last_run", "Run faster when under &9%25&e health.");
        }
    });
    public static final IPerk LAST_ATTACK = register("last_attack", new IPerk() {
        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.last_attack", "Deal more damage when under &9%25&e health.");
        }
    });
    public static final IPerk LAST_SHIELD = register("last_shield", new IPerk() {
        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.last_shield", "Get Resistance effect when under &9%25&e health.");
        }
    });
    public static final TimedEffectPerk LOVE = register("love", new TimedEffectPerk() {
        @Override
        public void grant(Player player, int level) {

        }

        @Override
        public void degrade(Player player) {

        }

        @Override
        public void affectEntity(Entity entity, int level) {
            if (!(entity instanceof LivingEntity e)) return;
            e.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, getAmount(level) * 100, getAmount(level), false, false, false));
        }

        private int getAmount(int level) {
            int el = 1;
            if (level >= 10) el++;
            if (level >= 20) el++;
            if (level >= 30) el++;
            if (level >= 40) el++;
            if (level >= 50) el++;
            if (level >= 60) el++;
            if (level >= 70) el++;
            if (level >= 80) el++;
            if (level >= 90) el++;
            if (level >= 100) el++;
            return el;
        }

        @Override
        public String getDescription(int level) {
            return Main.LANG.getString("perks.love", "Killing an entity gives Strength %a% effect. ")
                    .replace("%a%", Utilities.generateAmountText(getAmount(level), ChatColor.YELLOW + ""));
        }
    });
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
