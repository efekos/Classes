package dev.efekos.classes.registry.perk;

import java.util.Map;

@FunctionalInterface
public interface MapProvider<K,V> {

    Map<K,V> getMap();

}
