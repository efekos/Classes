package dev.efekos.classes.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.efekos.classes.Main;
import dev.efekos.classes.api.i.IModifier;
import dev.efekos.classes.api.i.IPerk;
import dev.efekos.classes.api.registry.ModifierRegistry;
import dev.efekos.classes.api.registry.PerkRegistry;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ClassManager {
    private static final Map<UUID, PlayerData> datas = new HashMap<>();

    public static Map<UUID, PlayerData> getDatas() {
        return datas;
    }

    public static Class getClass(UUID uuid){
        checkExistent(uuid);
        return Main.CLASSES.get(datas.get(uuid).getCurrentClass());
    }
    private static void checkLevelExistent(UUID id,Class clas){
        checkExistent(id);
        PlayerData data = datas.get(id);
        if (!data.getClassLevels().containsKey(clas.getUniqueId())) {
            data.getClassLevels().put(clas.getUniqueId(),new LevelData(0,0));
        }
        datas.put(id,data);
    }

    public static void levelUp(UUID id){
        checkExistent(id);
        if(!hasClass(id))return;
        PlayerData data = datas.get(id);
        Class clas = Main.CLASSES.get(data.getCurrentClass());
        assert clas != null;
        checkLevelExistent(id, clas);

        LevelData clasLevel = data.getClassLevels().get(clas.getUniqueId());
        clasLevel.setXp(0);
        clasLevel.setLevel(clasLevel.getLevel()+1);
        data.getClassLevels().put(clas.getUniqueId(),clasLevel);

        Player player = Bukkit.getPlayer(id);
        assert player != null;
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("level-up","&eYou got leveled up to level &b%level%&e in your current class!")
                .replace("%level%",clasLevel.getLevel()+"")
        ));

        for (ModifierApplier applier : clas.getModifiers()) {
            IModifier modifier = Main.MODIFIER_REGISTRY.get(applier.getModifierId());
            modifier.deapply(player);
            modifier.apply(player,clasLevel.getLevel(),applier.getValue());
        }
        for (PerkApplier perk : clas.getPerks()) {
            IPerk iPerk = Main.PERK_REGISTRY.get(perk.getPerkId());
            iPerk.degrade(player);
            iPerk.grant(player,clasLevel.getLevel());
        }
    }

    public static int getLevel(UUID id){
        checkExistent(id);
        if(!hasClass(id))return 0;
        PlayerData data = datas.get(id);
        Class clas = Main.CLASSES.get(data.getCurrentClass());
        assert clas != null;
        checkLevelExistent(id, clas);

        LevelData clasLevel = data.getClassLevels().get(clas.getUniqueId());
        return clasLevel.getLevel();
    }

    public static int getXp(UUID id){
        checkExistent(id);
        if(!hasClass(id))return 0;
        PlayerData data = datas.get(id);
        Class clas = Main.CLASSES.get(data.getCurrentClass());
        assert clas != null;
        checkLevelExistent(id, clas);

        LevelData clasLevel = data.getClassLevels().get(clas.getUniqueId());
        return clasLevel.getXp();
    }

    public static LevelData getLevelData(UUID id,UUID classId){
        checkExistent(id);
        checkLevelExistent(id,Main.CLASSES.get(classId));

        return datas.get(id).getClassLevels().get(classId);
    }

    public static void addExp(UUID id,int amount){
        checkExistent(id);
        if(!hasClass(id))return;
        PlayerData data = datas.get(id);
        Class clas = Main.CLASSES.get(data.getCurrentClass());
        assert clas != null;
        checkLevelExistent(id, clas);

        LevelData clasLevel = data.getClassLevels().get(clas.getUniqueId());
        clasLevel.setXp(clasLevel.getXp()+amount);


        int maxXP = clasLevel.getLevel() * 8 + 100;
        if(clasLevel.getXp()> maxXP){
            levelUp(id);
        }

        Player p = Bukkit.getPlayer(id);

        assert p != null;


        NamespacedKey key = new NamespacedKey(Main.getInstance(),p.getName()+"_status");
        BossBar bar = Bukkit.getBossBar(key);
        if(bar==null)bar = Bukkit.createBossBar(key,TranslateManager.translateColors("Level &2&l8 &r- Progress: &a%42"), BarColor.GREEN, BarStyle.SOLID);
        double progress = (double) clasLevel.getXp() /maxXP;

        bar.setTitle(TranslateManager.translateColors(Main.LANG.getString("level-progress","Level &2&l%lvl% &r- Progress: &a%progress%")
                        .replace("%lvl%",clasLevel.getLevel()+"")
                        .replace("%progress%", NumberFormat.getPercentInstance(new Locale(p.getLocale())).format(progress))
                ));
        bar.addPlayer(p);
        bar.setProgress((double) clasLevel.getXp() /maxXP);
        bar.setVisible(true);
        bar.setColor(BarColor.valueOf(Main.CONFIG.getString("bar.color","GREEN")));
        bar.setStyle(BarStyle.valueOf(Main.CONFIG.getString("bar.style","SOLID")));
        new BukkitRunnable(){
            private int i = 100;
            @Override
            public void run() {
                BossBar b = Bukkit.getBossBar(key);
                assert b != null;
                if(i!=0&&!b.isVisible()){cancel();return;}
                if(i==0&&b.isVisible()) {
                    b.setVisible(false);
                    cancel();
                    return;
                }
                i--;
            }
        }.runTaskTimer(Main.getInstance(),0,1);
    }

    public static boolean hasClass(UUID id){
        checkExistent(id);
        return datas.containsKey(id) && Main.CLASSES.get(datas.get(id).getCurrentClass())!=null;
    }

    public static boolean hasPerk(UUID id, IPerk perk){
        if(!hasClass(id))return false;
        Class clas = getClass(id);

        for (PerkApplier applier : clas.getPerks()) {
            if(applier.getPerkId().equals(Main.PERK_REGISTRY.idOf(perk)))return true;
        }
        return false;
    }

    public static void removeClass(UUID id){
        datas.get(id).setCurrentClass(null);
    }

    private static void checkExistent(UUID id){
        if(!datas.containsKey(id)) datas.put(id,new PlayerData(null));
    }

    public static void setClass(UUID id,Class clas){
        checkExistent(id);
        PlayerData data = datas.get(id);
        data.setCurrentClass(clas.getUniqueId());
        datas.put(id,data);
    }

    public static void save(Main main){
        String path = main.getDataFolder().getAbsolutePath()+"\\PlayerClasses.json";

        File file = new File(path);

        try {
            file.createNewFile();

            Writer writer = new FileWriter(file,false);

            new Gson().toJson(datas,writer);

            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void load(Main main) {
        String path = main.getDataFolder().getAbsolutePath() + "\\PlayerClasses.json";
        File file = new File(path);

        if (!file.exists()) return;

        try {
            Reader reader = new FileReader(file);

            Gson gson = new GsonBuilder().create();

            Type mapType = new TypeToken<Map<String, PlayerData>>() {}.getType();

            Map<String, PlayerData> map = gson.fromJson(reader, mapType);

            for (String uuidString : map.keySet()) {
                UUID uuid = UUID.fromString(uuidString);
                datas.put(uuid, map.get(uuidString));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
