package dev.efekos.classes.data;

public class LevelData {
    private int level;
    private int xp;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public LevelData(int level, int xp) {
        this.level = level;
        this.xp = xp;
    }
}
