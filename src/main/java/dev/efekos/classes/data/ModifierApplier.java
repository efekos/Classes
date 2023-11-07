package dev.efekos.classes.data;

import org.bukkit.NamespacedKey;

public class ModifierApplier {
    private String modifierId;
    private double value;

    public ModifierApplier(NamespacedKey modifierId, int value) {
        this.modifierId = modifierId.toString();
        this.value = value;
    }

    public ModifierApplier(NamespacedKey modifierId, double value) {
        this.modifierId = modifierId.toString();
        this.value = value;
    }

    public NamespacedKey getModifierId() {
        return NamespacedKey.fromString(modifierId);
    }

    public void setModifierId(NamespacedKey modifierId) {
        this.modifierId = modifierId.toString();
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
