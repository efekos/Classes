package dev.efekos.classes.menu;

import dev.efekos.classes.Main;
import me.efekos.simpler.menu.MenuData;
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

import java.util.List;

public class ChooseClassMenu extends PaginatedMenu{
    public ChooseClassMenu(MenuData data) {
        super(data);
    }

    @Override
    protected List<ItemStack> setItems() {
        return Main.CLASSES.getAll().stream().map(clas->{
            ItemStack item = clas.getIcon();

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(TranslateManager.translateColors(Main.LANG.getString("menus.choose_class.class-name","&e%name%").replace("%name%",clas.getName())));
            meta.setLore(Main.LANG.getStringList("menus.choose_class.class-lore").stream()
                    .map(s -> s.replace("%desc%",clas.getDescription()).replace("%modifiers%",clas.getModifiers().size()+"").replace("%perks%",clas.getPerks().size()+""))
                    .map(s -> TranslateManager.translateColors(s))
                    .toList()
            );

            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(),"className"), PersistentDataType.STRING,clas.getName());
            item.setItemMeta(meta);

            return item;
        }).toList();
    }

    private final NamespacedKey key = new NamespacedKey(Main.getInstance(),"className");
    private boolean choosedOne = false;

    @Override
    public String getTitle() {
        return Main.LANG.getString("menus.choose_class.title","Choose a Class!");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);

        ItemStack item = e.getCurrentItem();
        if(!item.hasItemMeta())return;
        ItemMeta meta = item.getItemMeta();
        if(!meta.getPersistentDataContainer().has(key,PersistentDataType.STRING))return;
        String name = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        Bukkit.dispatchCommand(owner,"classes:class join "+name);
        choosedOne = true;
        owner.closeInventory();
        owner.playSound(owner, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER,100,1);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        if(!choosedOne&&Main.CONFIG.getBoolean("class-required",true)) owner.openInventory(inventory);
    }

    @Override
    public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

    }
}
