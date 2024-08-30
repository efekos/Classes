package dev.efekos.classes.registry.perk;

import dev.efekos.classes.api.i.IPerk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AffecterEffectPerk implements IPerk, EntityAffecter {

    private final EntityAffecter affecter;
    private final DescriptionProvider descriptionProvider;

    public AffecterEffectPerk(EntityAffecter affecter, DescriptionProvider descriptionProvider) {
        this.affecter = affecter;
        this.descriptionProvider = descriptionProvider;
    }

    @Override
    public String getDescription(int i) {
        return descriptionProvider.getDescription(i);
    }

    @Override
    public void grant(Player player, int i) {

    }

    @Override
    public void degrade(Player player) {

    }

    public void affectEntity(Entity entity, int level) {
        affecter.affectEntity(entity, level);
    }

}
