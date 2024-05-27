package dev.efekos.classes.registry.perk;

import dev.efekos.classes.api.i.IPerk;
import org.bukkit.entity.Entity;

public interface TimedEffectPerk extends IPerk {
    void affectEntity(Entity entity, int level);
}
