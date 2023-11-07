package dev.efekos.classes.api.i;

import org.bukkit.entity.Player;

public interface IPerk {
    void grant(Player player,int level);
    void degrade(Player player);
    String getDescription(int level);
}
