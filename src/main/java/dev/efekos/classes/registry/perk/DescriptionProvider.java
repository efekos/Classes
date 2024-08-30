package dev.efekos.classes.registry.perk;

@FunctionalInterface
public interface DescriptionProvider {

    String getDescription(int level);

}
