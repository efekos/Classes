package dev.efekos.classes.api.i;

import org.bukkit.NamespacedKey;

public interface IRegistry<T> {
    <K extends T > K register(NamespacedKey key,K object);
    T get(NamespacedKey key);
    NamespacedKey idOf(T object);
}
