package dev.efekos.classes.registry.perk;

import org.bukkit.entity.Entity;

@FunctionalInterface
public interface EntityAffecter {

    void affectEntity(Entity entity,int level);

}
