package dev.efekos.classes.registry.perk;

import dev.efekos.classes.api.i.IPerk;
import org.bukkit.entity.Player;

public class HandledPerk implements IPerk {

    private final DescriptionProvider description;

    public HandledPerk(DescriptionProvider description) {
        this.description = description;
    }

    @Override
    public void grant(Player player, int i) {

    }

    @Override
    public void degrade(Player player) {

    }

    @Override
    public String getDescription(int i) {
        return description.getDescription(i);
    }
}
