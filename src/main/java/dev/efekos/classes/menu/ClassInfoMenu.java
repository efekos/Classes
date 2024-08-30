package dev.efekos.classes.menu;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.*;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static me.efekos.simpler.translation.TranslateManager.translateColors;

public class ClassInfoMenu extends Menu {
    private String className = "";

    public ClassInfoMenu(MenuData data) {
        super(data);
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public String getTitle() {
        return Main.LANG.getString("menus.class_info.title", "Class Information");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getClick() != ClickType.LEFT) return;

        Player player = (Player) e.getWhoClicked();

        if (!Arrays.asList(39, 40, 41).contains(e.getSlot())) return;
        switch (e.getCurrentItem().getType()) {
            case LIME_DYE -> {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100, 1);
                Bukkit.dispatchCommand(player, "class join " + className);
                refresh();
            }
            case RED_DYE -> {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100, 1);
                Bukkit.dispatchCommand(player, "class leave");
                refresh();
            }
            case BUNDLE -> {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100, 1);
                Bukkit.dispatchCommand(player, "class kit " + className + " get");
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {

    }

    @Override
    public void onOpen(InventoryOpenEvent inventoryOpenEvent) {

    }

    @Override
    public void fill() {

        Class clas = Main.CLASSES.get(((UUID) data.get("class")));
        className = clas.getName();
        boolean hasClass = ClassManager.hasClass(owner.getUniqueId());
        boolean ownClass = hasClass && ClassManager.getClass(owner.getUniqueId()).getUniqueId() == clas.getUniqueId();

        ItemStack icon = clas.getIcon();
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(TranslateManager.translateColors(Main.LANG.getString("menus.class_info.class-info.name", "&eClass Info").replace("%class%", clas.getName())));
        meta.setLore(
                Main.LANG.getStringList("menus.class_info.class-info.desc")
                        .stream()
                        .map(s -> TranslateManager.translateColors(s
                                .replace("%name%", clas.getName())
                                .replace("%desc%", clas.getDescription())
                        )).toList()
        );
        icon.setItemMeta(meta);
        inventory.setItem(13, icon);

        LevelData levelData = ClassManager.getLevelData(owner.getUniqueId(), clas.getUniqueId());

        int maxXP = levelData.getLevel() * 8 + 100;
        if (levelData.getXp() != 0 || levelData.getLevel() != 0) inventory.setItem(22, createSkull(data.getOwner(),
                TranslateManager.translateColors(Main.LANG.getString("menus.class_info.player-info.name", "&eYour Stats")),
                Main.LANG.getStringList("menus.class_info.player-info.desc").stream().map(s ->
                        TranslateManager.translateColors(s)
                                .replace("%lvl%", "" + levelData.getLevel())
                                .replace("%xp%", "" + levelData.getXp())
                                .replace("%xp_prog%", "%" + Math.round(((float) levelData.getXp() / maxXP) * 100))
                ).toArray(String[]::new)
        ));

        List<String> negatives = new ArrayList<>();
        List<String> positives = new ArrayList<>();
        List<String> perks = new ArrayList<>();

        for (PerkApplier p : clas.getPerks()) {
            IPerk perk = Main.PERK_REGISTRY.get(p.getPerkId());
            perks.add(ChatColor.YELLOW + translateColors(perk.getDescription(levelData.getLevel())));
        }

        for (ModifierApplier applier : clas.getModifiers()) {
            IModifier modifier = Main.MODIFIER_REGISTRY.get(applier.getModifierId());

            if (modifier.isPositive(levelData.getLevel(), applier.getValue()))
                positives.add(ChatColor.YELLOW + TranslateManager.translateColors(modifier.getDescription(levelData.getLevel(), applier.getValue())));
            else
                negatives.add(ChatColor.YELLOW + translateColors(modifier.getDescription(levelData.getLevel(), applier.getValue())));
        }

        inventory.setItem(30, createItem(Material.RED_CONCRETE, translateColors(Main.LANG.getString("menus.class_info.negative-modifiers", "&cNegative Modifiers")), negatives.toArray(String[]::new)));
        inventory.setItem(31, createItem(Material.BLUE_CONCRETE, translateColors(Main.LANG.getString("menus.class_info.perks", "&bPerks")), perks.toArray(String[]::new)));
        inventory.setItem(32, createItem(Material.LIME_CONCRETE, translateColors(Main.LANG.getString("menus.class_info.positive-modifiers", "&aPositive Modifiers")), positives.toArray(String[]::new)));

        ItemStack kitItem = createItem(Material.BUNDLE, translateColors(Main.LANG.getString("menus.class_info.kit", "&eClick to Get The Kit!")));
        BundleMeta kitItemMeta = (BundleMeta) kitItem.getItemMeta();
        clas.getKitItems().forEach(kitItemMeta::addItem);
        kitItem.setItemMeta(kitItemMeta);

        if (!clas.getKitItems().isEmpty()) inventory.setItem(40, kitItem);
        if (ownClass)
            inventory.setItem(39, createItem(Material.RED_DYE, translateColors(Main.LANG.getString("menus.class_info.leave", "&cLeave Class"))));
        else if (!hasClass)
            inventory.setItem(41, createItem(Material.LIME_DYE, translateColors(Main.LANG.getString("menus.class_info.join", "&aJoin This Class"))));

        fillEmptyWith(createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
    }
}
