package dev.efekos.classes.registry.perk;

import dev.efekos.classes.api.i.IPerk;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MapManagedPerk implements IPerk {

    private final DescriptionProvider descriptionProvider;
    private final MapProvider<UUID,Integer> mapProvider;

    public MapManagedPerk(DescriptionProvider descriptionProvider, MapProvider<UUID,Integer> mapProvider) {
        this.descriptionProvider = descriptionProvider;
        this.mapProvider = mapProvider;
    }

    @Override
    public void grant(Player player, int i) {
        mapProvider.getMap().put(player.getUniqueId(),i);
    }

    @Override
    public void degrade(Player player) {
        mapProvider.getMap().remove(player.getUniqueId());
    }

    @Override
    public String getDescription(int i) {
        return descriptionProvider.getDescription(i);
    }
}
