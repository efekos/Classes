package dev.efekos.classes.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private Map<UUID, LevelData> classLevels = new HashMap<>();
    private UUID currentClass;

    public PlayerData(UUID currentClass) {
        this.currentClass = currentClass;
    }

    public UUID getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(UUID currentClass) {
        this.currentClass = currentClass;
    }

    public Map<UUID, LevelData> getClassLevels() {
        return classLevels;
    }

    public void setClassLevels(Map<UUID, LevelData> classLevels) {
        this.classLevels = classLevels;
    }

}
