package dev.efekos.classes.events;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.ModifierApplier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ModifierApplyingEvents implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        if(!ClassManager.hasClass(p.getUniqueId())) return;

        Class aClass = ClassManager.getClass(p.getUniqueId());

        for (ModifierApplier modifier : aClass.getModifiers()) {
            Main.MODIFIER_REGISTRY.get(modifier.getModifierId()).apply(p,ClassManager.getLevel(p.getUniqueId()), modifier.getValue());
        }
    }
}
