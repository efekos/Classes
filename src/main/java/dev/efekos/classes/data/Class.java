package dev.efekos.classes.data;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.ILevelCriteria;
import dev.efekos.simple_ql.annotation.Primary;
import dev.efekos.simple_ql.data.AdaptedList;
import dev.efekos.simple_ql.data.Table;
import dev.efekos.simple_ql.data.TableRow;
import dev.efekos.simple_ql.implementor.PrimitiveImplementors;
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

import java.util.List;
import java.util.UUID;

public class Class extends TableRow<Class> {

    @Primary
    private UUID uniqueId;
    private String name;
    private String description;
    private AdaptedList<String> blockedEnchantments = new AdaptedList<>(PrimitiveImplementors.STRING);
    private AdaptedList<String> blockedMaterials = new AdaptedList<>(PrimitiveImplementors.STRING);
    private AdaptedList<String> blockedPotions = new AdaptedList<>(PrimitiveImplementors.STRING);
    private AdaptedList<ModifierApplier> modifiers = new AdaptedList<>(ModifierApplierImplementor.INSTANCE);
    private AdaptedList<String> perks = new AdaptedList<>(PrimitiveImplementors.STRING);
    private AdaptedList<Item> kitItems = new AdaptedList<>(ItemImplementor.INSTANCE);
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
        this.kitItems = new AdaptedList<>(kitItems.stream().map(Class::toContent).toList(),ItemImplementor.INSTANCE);
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
        return perks.stream().toList();
    }

    public void setPerks(List<String> perks) {
        this.perks = new AdaptedList<>(perks,PrimitiveImplementors.STRING);
        markDirty("perks");
    }

    public void addModifier(ModifierApplier applier) {
        modifiers.add(applier);
        markDirty("modifiers");
    }

    public List<ModifierApplier> getModifiers() {
        return modifiers.stream().toList();
    }

    public void setModifiers(List<ModifierApplier> modifiers) {
        this.modifiers = new AdaptedList<>(modifiers,ModifierApplierImplementor.INSTANCE);
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

    public void setBlockedEnchantments(List<Enchantment> blockedEnchantments) {
        this.blockedEnchantments = new AdaptedList<>(blockedEnchantments.stream().map(Enchantment::getKey).map(NamespacedKey::toString).toList(),PrimitiveImplementors.STRING);
        markDirty("blockedEnchantments");
    }

    public void setBlockedMaterials(List<Material> blockedMaterials) {
        this.blockedMaterials = new AdaptedList<>(blockedMaterials.stream().map(Material::getKey).map(NamespacedKey::toString).toList(),PrimitiveImplementors.STRING);
        markDirty("blockedMaterials");
    }

    public void setBlockedPotions(List<PotionEffectType> blockedPotions) {
        this.blockedPotions = new AdaptedList<>(blockedPotions.stream().map(PotionEffectType::getKey).map(NamespacedKey::toString).toList(),PrimitiveImplementors.STRING);
        markDirty("blockedPotions");
    }

    public void addPerk(String string) {
        this.perks.add(string);
        markDirty("perks");
    }

}