package dev.efekos.classes.events;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.registry.ClassesPerks;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HandlingEvents implements Listener {
    public static Map<UUID, Integer> playersToBoostSnowball = new HashMap<>();
    public static Map<UUID, Integer> playersToBoostPearl = new HashMap<>();
    public static Map<UUID, Integer> playersToBoostArrow = new HashMap<>();

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent e) {
        playersToBoostArrow.remove(e.getEntity());
        playersToBoostPearl.remove(e.getEntity());
        playersToBoostSnowball.remove(e.getEntity());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Player p;
        switch (e.getEntityType()) {
            case ARROW:
                Arrow arrow = (Arrow) e.getEntity();
                if (!(arrow.getShooter() instanceof Player)) break;
                p = (Player) arrow.getShooter();
                if (!playersToBoostArrow.containsKey(p)) break;

                arrow.setVelocity(arrow.getVelocity().add(arrow.getVelocity().clone()));
                break;
            case SNOWBALL:
                Snowball snowball = (Snowball) e.getEntity();
                if (!(snowball.getShooter() instanceof Player)) break;
                p = (Player) snowball.getShooter();
                if (!playersToBoostSnowball.containsKey(p)) break;

                snowball.setVelocity(snowball.getVelocity().add(snowball.getVelocity().clone()));
                break;
            case ENDER_PEARL:
                EnderPearl pearl = (EnderPearl) e.getEntity();
                if (!(pearl.getShooter() instanceof Player)) break;
                p = (Player) pearl.getShooter();
                if (!playersToBoostPearl.containsKey(p)) break;

                pearl.setVelocity(pearl.getVelocity().add(pearl.getVelocity().clone()));
                break;
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        if ((p.getHealth() / Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue()) < 0.25) {
            if (ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.LAST_RUN))
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0, false, false, false));
            if (ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.LAST_ATTACK))
                p.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 100, 0, false, false, false));
            if (ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.LAST_SHIELD))
                p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 0, false, false, false));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.getBlock().setMetadata("placenByPlayer", new FixedMetadataValue(Main.getInstance(), true));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        World world = e.getPlayer().getWorld();

        if (!e.getFrom().getBlock().equals(e.getTo().getBlock())) return;

        if (Math.random() < 0.3 && ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.AURA)) {
            Block block = world.getBlockAt(p.getLocation().add(
                    Math.round(Math.random() * 10) - 5,
                    Math.round(Math.random() * 4) - 2,
                    Math.round(Math.random() * 10) - 5
            ));
            ClassesPerks.AURA.affectBlock(world, block);
        }
    }

    private final Map<Player, Integer> kills = new HashMap<>();

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) return;

        if (ClassManager.hasPerk(killer.getUniqueId(), ClassesPerks.LOVE)) {
            ClassesPerks.LOVE.affectEntity(killer, ClassManager.getLevel(killer.getUniqueId()));
        }

        if (!ClassManager.hasPerk(killer.getUniqueId(), ClassesPerks.LIFE_STEALER)) return;
        if (!kills.containsKey(killer)) kills.put(killer, ClassManager.getLevel(killer.getUniqueId()));

        Integer i = kills.get(killer);

        if (i == 0) {
            if (killer.getHealth() != Objects.requireNonNull(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue())
                killer.setHealth(killer.getHealth() + 1);
            killer.sendHealthUpdate();
            kills.put(killer, ClassesPerks.LIFE_STEALER.getMaxKill(ClassManager.getLevel(killer.getUniqueId())));
        } else kills.put(killer, i - 1);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;
        Entity entity = e.getEntity();

        if (ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.BURNER))
            ClassesPerks.BURNER.affectEntity(entity, ClassManager.getLevel(p.getUniqueId()));
        if (ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.FROSTER))
            ClassesPerks.FROSTER.affectEntity(entity, ClassManager.getLevel(p.getUniqueId()));
        if (ClassManager.hasPerk(p.getUniqueId(), ClassesPerks.POISONER))
            ClassesPerks.POISONER.affectEntity(entity, ClassManager.getLevel(p.getUniqueId()));
    }
}
