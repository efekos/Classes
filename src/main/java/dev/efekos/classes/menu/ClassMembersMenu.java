package dev.efekos.classes.menu;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.LevelData;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.PaginatedMenu;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.*;

public class ClassMembersMenu extends PaginatedMenu {
    public ClassMembersMenu(MenuData data) {
        super(data);
    }

    @Override
    public String getTitle() {
        UUID classId = (UUID) data.get("class");
        Optional<Class> row = Main.CLASSES.getRow(classId);
        String name = row.isPresent() ? row.get().getName() : "Unknown Class";
        return Main.LANG.getString("menus.class_members.title", "Members of the class %class%").replace("%class%", name);
    }

    @Override
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {

    }

    @Override
    protected List<ItemStack> setItems() {
        UUID classId = (UUID) data.get("class");
        List<ItemStack> items = new ArrayList<>();
        ClassManager.getDatas().forEach((uuid, playerData) -> {
            if (Objects.nonNull(playerData.getCurrentClass()) && playerData.getCurrentClass().equals(classId)) {
                Player p = Bukkit.getPlayer(uuid);

                LevelData levelData = ClassManager.getLevelData(p.getUniqueId(), classId);

                items.add(createSkull(p, TranslateManager.translateColors(Main.LANG.getString("menus.class_members.member-name", "&e%name%").replace("%name%", p.getDisplayName())),
                        Main.LANG.getStringList("menus.class_members.member-lore").stream().map(s -> s
                                .replace("%xp%", levelData.getXp() + "")
                                .replace("%lvl%", levelData.getLevel() + "")
                                .replace("%xp_prog%", NumberFormat.getPercentInstance().format(levelData.getXp() / (levelData.getLevel() * 8L + 100L)))
                        ).map(TranslateManager::translateColors).toArray(String[]::new)
                ));
            }
        });
        return items;
    }

    @Override
    public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

    }
}
