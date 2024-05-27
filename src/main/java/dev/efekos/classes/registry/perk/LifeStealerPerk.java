package dev.efekos.classes.registry.perk;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.api.i.IPerk;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class LifeStealerPerk implements IPerk {
    @Override
    public void grant(Player player, int level) {

    }

    @Override
    public void degrade(Player player) {

    }

    public int getMaxKill(int level) {
        int amount = 4;
        if (level >= 25) amount--;
        if (level >= 50) amount--;
        if (level >= 75) amount--;
        if (level >= 100) amount--;
        return amount;
    }

    @Override
    public String getDescription(int level) {

        return Main.LANG.getString("perks.life_stealer", "&eGet &b1 &ehealth &5(&dhalf of an heart&5) &eeach %a% kill")
                .replace("%a%", Utilities.generateAmountText(getMaxKill(level), ChatColor.YELLOW + ""));
    }
}
