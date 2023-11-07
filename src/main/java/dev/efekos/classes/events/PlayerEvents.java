package dev.efekos.classes.events;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.menu.ChooseClassMenu;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerEvents implements Listener {

    public static Map<UUID,Double> playersToArrowBoost = new HashMap<>();

    @EventHandler
    public void onEntitySpawn(ProjectileLaunchEvent e){
        Entity entity = e.getEntity();
        if(!(entity instanceof Arrow arrow)) return;

        if(!(arrow.getShooter() instanceof Player p)) return;
        if(!playersToArrowBoost.containsKey(p.getUniqueId()))return;
        Double i = playersToArrowBoost.get(p.getUniqueId());
        AtomicReference<Vector> firstArrowVector = new AtomicReference<>(arrow.getVelocity());
        AtomicInteger counter = new AtomicInteger(0);
        int maxCounter = (int) Math.round(i*20);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(arrow.getVelocity().isZero()||maxCounter== counter.get()) {
                    cancel();return;
                }
                counter.set(counter.get()+1);
                arrow.setVelocity(firstArrowVector.get());
            }
        }.runTaskTimer(Main.getInstance(),0,1);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(Main.CONFIG.getBoolean("class-required",true)&&!ClassManager.hasClass(p.getUniqueId())){
            MenuManager.Open(p, ChooseClassMenu.class);
        }
    }
}