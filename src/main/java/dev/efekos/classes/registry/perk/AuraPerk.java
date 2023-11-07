package dev.efekos.classes.registry.perk;

import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IPerk;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class AuraPerk implements IPerk {

    private final String description;
    private final Consumer<Block> consumer;

    public AuraPerk(String description, Consumer<Block> consumer) {
        this.description = description;
        this.consumer = consumer;
    }

    public void affectBlock(Block block){
        consumer.accept(block);
    }

    public int getBlockDistance(int level){
        return Math.round((float) level/2);
    }

    @Override
    public void grant(Player player, int level) {

    }

    @Override
    public void degrade(Player player) {

    }

    @Override
    public String getDescription(int level) {
        return description
                .replace("%a%", Utilities.generateAmountText(getBlockDistance(level), ChatColor.YELLOW+""));
    }
}
