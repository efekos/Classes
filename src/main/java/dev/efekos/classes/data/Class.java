package dev.efekos.classes.data;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.ILevelCriteria;
import dev.efekos.simple_ql.annotation.Primary;
import dev.efekos.simple_ql.data.Table;
import dev.efekos.simple_ql.data.TableRow;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Class extends TableRow<Class> {

    @Primary
    private UUID uniqueId;
    private String name;
    private String description;
    private final List<String> blockedEnchantments = new ArrayList<>();
    private final List<String> blockedMaterials = new ArrayList<>();
    private final List<String> blockedPotions = new ArrayList<>();
    private List<ModifierApplier> modifiers = new ArrayList<>();
    private List<String> perks = new ArrayList<>();
    private List<Item> kitItems = new ArrayList<>();
    private String levelCriteria;
    private Item icon;

    public Class(java.lang.Class<Class> clazz, Table<Class> parentTable) {
        super(clazz, parentTable);
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
        markDirty("uniqueId");
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ItemStack getIcon() {
        return Bukkit.getServer().getItemFactory().createItemStack(icon.getId() + icon.getTag());
    }

    public void setIcon(ItemStack icon) {
        this.icon = toContent(icon);
        markDirty("icon");
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
        markDirty("kitItems");
    }

    private static @NotNull Item toContent(ItemStack itemStack) {
        return new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(), ItemTag.ofNbt(itemStack.getItemMeta().getAsString()));
    }

    public void addKitItem(ItemStack item) {
        kitItems.add(toContent(item));
        markDirty("kitItems");
    }

    public ILevelCriteria getLevelCriteria() {
        return Main.CRITERIA_REGISTRY.get(NamespacedKey.fromString(levelCriteria));
    }

    public void setLevelCriteria(ILevelCriteria levelCriteria) {
        this.levelCriteria = Main.CRITERIA_REGISTRY.idOf(levelCriteria).toString();
        markDirty("levelCriteria");
    }

    public List<String> getPerks() {
        return perks;
    }

    public void setPerks(List<String> perks) {
        this.perks = perks;
        markDirty("perks");
    }

    public void addModifier(ModifierApplier applier) {
        modifiers.add(applier);
        markDirty("modifiers");
    }

    public List<ModifierApplier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<ModifierApplier> modifiers) {
        this.modifiers = modifiers;
        markDirty("modifiers");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        markDirty("name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        markDirty("description");
    }

    public List<Enchantment> getBlockedEnchantments() {
        return blockedEnchantments.stream().map(Registry.ENCHANTMENT::match).toList();
    }

    public List<Material> getBlockedMaterials() {
        return blockedMaterials.stream().map(Material::matchMaterial).toList();
    }

    public List<PotionEffectType> getBlockedPotions() {
        return blockedPotions.stream().map(Registry.EFFECT::match).toList();
    }

}
