package dev.efekos.classes.data;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.ILevelCriteria;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Class {
    private final UUID uniqueId;
    private String name;
    private String description;
    private List<Enchantment> blockedEnchantments = new ArrayList<>();
    private List<Material> blockedMaterials = new ArrayList<>();
    private List<PotionEffectType> blockedPotions = new ArrayList<>();
    private List<ModifierApplier> modifiers = new ArrayList<>();
    private List<PerkApplier> perks = new ArrayList<>();
    private List<Item> kitItems = new ArrayList<>();
    private String levelCriteria;
    private Item icon;

    public Class(String name, String description, ILevelCriteria criteria) {
        this.name = name;
        this.description = description;
        this.levelCriteria = Main.CRITERIA_REGISTRY.idOf(criteria).toString();
        this.icon = new Item("minecraft:iron_sword", 1, ItemTag.ofNbt("{}"));
        this.uniqueId = UUID.randomUUID();
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ItemStack getIcon() {
        return Bukkit.getServer().getItemFactory().createItemStack(icon.getId() + icon.getTag());
    }

    public void setIcon(ItemStack icon) {
        this.icon = toContent(icon);
    }

    public List<ItemStack> getKitItems() {
        return kitItems.stream().map(itemContent -> {
                    ItemStack stack = Bukkit.getServer().getItemFactory().createItemStack(itemContent.getId()+ itemContent.getTag().getNbt());
                    stack.setAmount(itemContent.getCount());
                    return stack;
                }
        ).toList();
    }

    public void setKitItems(List<ItemStack> kitItems) {
        this.kitItems = kitItems.stream().map(Class::toContent).toList();
    }

    private static @NotNull Item toContent(ItemStack itemStack) {
        return new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(), ItemTag.ofNbt(itemStack.getItemMeta().getAsString()));
    }

    public void addKitItem(ItemStack item) {
        kitItems.add(toContent(item));
    }

    public ILevelCriteria getLevelCriteria() {
        return Main.CRITERIA_REGISTRY.get(NamespacedKey.fromString(levelCriteria));
    }

    public void setLevelCriteria(ILevelCriteria levelCriteria) {
        this.levelCriteria = Main.CRITERIA_REGISTRY.idOf(levelCriteria).toString();
    }

    public List<PerkApplier> getPerks() {
        return perks;
    }

    public void setPerks(List<PerkApplier> perks) {
        this.perks = perks;
    }

    public void addModifier(ModifierApplier applier) {
        modifiers.add(applier);
    }

    public List<ModifierApplier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<ModifierApplier> modifiers) {
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Enchantment> getBlockedEnchantments() {
        return blockedEnchantments;
    }

    public void setBlockedEnchantments(List<Enchantment> blockedEnchantments) {
        this.blockedEnchantments = blockedEnchantments;
    }

    public List<Material> getBlockedMaterials() {
        return blockedMaterials;
    }

    public void setBlockedMaterials(List<Material> blockedMaterials) {
        this.blockedMaterials = blockedMaterials;
    }

    public List<PotionEffectType> getBlockedPotions() {
        return blockedPotions;
    }

    public void setBlockedPotions(List<PotionEffectType> blockedPotions) {
        this.blockedPotions = blockedPotions;
    }
}
