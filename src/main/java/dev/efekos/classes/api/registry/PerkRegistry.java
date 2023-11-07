package dev.efekos.classes.api.registry;

import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.api.i.IRegistry;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

public final class PerkRegistry implements IRegistry<IPerk> {
    private static PerkRegistry instance;

    private final Map<NamespacedKey, IPerk> perks = new HashMap<>();

    public Map<NamespacedKey, IPerk> getAll() {
        return perks;
    }

    public PerkRegistry() {
        instance = this;
    }

    public static PerkRegistry getInstance() {
        return instance;
    }

    @Override
    public <T extends IPerk> T register(NamespacedKey key, T object) {
        perks.put(key,object);
        return object;
    }

    @Override
    public NamespacedKey idOf(IPerk object) {
        for (NamespacedKey key : perks.keySet()) {
            if(perks.get(key).equals(object)) return key;
        }
        return null;
    }

    @Override
    public IPerk get(NamespacedKey key) {
        return perks.get(key);
    }
}
