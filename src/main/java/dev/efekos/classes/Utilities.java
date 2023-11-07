package dev.efekos.classes;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlastingRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Utilities {
    public static Material getMaterialByKey(String key){
        for (Material material : Material.values()) {
            if(Objects.equals(key, material.getKey().getNamespace() + ":" + material.getKey().getKey())) return  material;
        }
        return null;
    }

    public static String generatePercentageText(int p,String end){
        return generatePercentageText(p,end,false);
    }

    public static String generatePercentageText(int p,String end,boolean nautral){
        int change = p - 100;
        String start;
        if(change>=0) start = ChatColor.GREEN+"+%";
        else {
            start = ChatColor.RED+"-%";
            change =-change;
        }
        return nautral?"&9%"+change+end:start+change+end;
    }

    public static String generateAmountText(int a,String end){
        return ChatColor.AQUA+""+a+end;
    }

    public static BaseComponent[] makeComponentsForValue(String baseText, String searchText, BaseComponent replacement){
        BaseComponent[] components = TextComponent.fromLegacyText(baseText);

        for (int i = 0; i < components.length; i++) {
            BaseComponent component = components[i];

            if(component.toPlainText().contains(searchText)){
                components[i] = replacement;
            }
        }

        return components;
    }
}
