package dev.efekos.classes.data;

import org.bukkit.NamespacedKey;

public class PerkApplier {
    private String perkId;

    public PerkApplier(NamespacedKey perkId) {
        this.perkId = perkId.toString();
    }

    public NamespacedKey getPerkId() {
        return NamespacedKey.fromString(perkId);
    }

    public void setPerkId(NamespacedKey perkId) {
        this.perkId = perkId.toString();
    }
}
