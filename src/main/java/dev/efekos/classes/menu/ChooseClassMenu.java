package dev.efekos.classes.menu;

import dev.efekos.classes.Main;
import dev.efekos.simple_ql.query.QueryBuilder;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.menu.PaginatedMenu;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class ChooseClassMenu extends PaginatedMenu {
    private final NamespacedKey key = new NamespacedKey(Main.getInstance(), "className");
    private boolean choseOne = false;

    public ChooseClassMenu(MenuData data) {
        super(data);
    }

    @Override
    protected List<ItemStack> setItems() {
        return Main.CLASSES.query(new QueryBuilder().sortAscending("name").getQuery()).result().stream().map(clas -> {
            ItemStack item = clas.getIcon();

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(TranslateManager.translateColors(Main.LANG.getString("menus.choose_class.class-name", "&e%class%").replace("%class%", clas.getName())));
            meta.setLore(Main.LANG.getStringList("menus.choose_class.class-lore").stream()
                    .map(s -> s
                            .replace("%desc%", clas.getDescription())
                            .replace("%modifiers%", clas.getModifiers().size() + "")
                            .replace("%perks%", clas.getPerks().size() + "")
                            .replace("%class%", clas.getName())
                    )
                    .map(TranslateManager::translateColors)
                    .toList()
            );

            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "className"), PersistentDataType.STRING, clas.getName());
            item.setItemMeta(meta);

            return item;
        }).toList();
    }

    @Override
    public String getTitle() {
        return Main.LANG.getString("menus.choose_class.title", "Choose a Class!");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);

        ItemStack item = e.getCurrentItem();
        if (!item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
        String name = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        Bukkit.dispatchCommand(owner, "class " + name + " join");
        choseOne = true;
        owner.closeInventory();
        owner.playSound(owner, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100, 1);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        if (owner == null) return;
        if (!choseOne && Main.CONFIG.getBoolean("class-required", true)) {
            UUID id = owner.getUniqueId();
            new BukkitRunnable() {
                @Override
                public void run() {
                    MenuManager.Open(Main.getInstance().getServer().getPlayer(id), ChooseClassMenu.class);
                }
            }.runTaskLater(Main.getInstance(), 2);
        }
    }

    @Override
    public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

    }
}
