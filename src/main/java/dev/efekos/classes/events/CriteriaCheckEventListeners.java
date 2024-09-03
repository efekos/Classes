package dev.efekos.classes.events;

import dev.efekos.classes.Main;
import dev.efekos.classes.api.event.CriteriaCheckEvent;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import dev.efekos.classes.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class CriteriaCheckEventListeners implements Listener {
    public void onPlayerDoAnything(Event e, Player p) {
        if (!ClassManager.hasClass(p.getUniqueId())) return;
        PlayerData data = ClassManager.getDatas().get(p.getUniqueId());
        Class clas = Main.CLASSES.getRow(data.getCurrentClass()).get();

        assert clas != null;
        Bukkit.getServer().getPluginManager().callEvent(new CriteriaCheckEvent(p, clas.getLevelCriteria(), e));
    }

    @EventHandler
    public void c(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        onPlayerDoAnything(e, e.getEntity().getKiller());
    }

    @EventHandler
    public void b(PlayerStatisticIncrementEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void d(PlayerBedEnterEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void e(PlayerMoveEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void f(PlayerFishEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void g(PlayerVelocityEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void h(PlayerRiptideEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void i(PlayerBedLeaveEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void j(PlayerToggleSneakEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void l(PlayerJoinEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void m(PlayerItemDamageEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void n(PlayerLoginEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void o(PlayerBucketEntityEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void p(PlayerShearEntityEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void r(PlayerExpChangeEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void s(PlayerKickEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void t(PlayerItemMendEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void u(PlayerQuitEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void v(PlayerRegisterChannelEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void y(PlayerUnregisterChannelEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void z(PlayerAdvancementDoneEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void aa(PlayerGameModeChangeEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ab(PlayerChangedMainHandEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ac(PlayerItemConsumeEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ad(PlayerHarvestBlockEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ae(PlayerAnimationEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void af(PlayerRespawnEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ah(PlayerSwapHandItemsEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ai(PlayerCommandPreprocessEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void aj(PlayerLocaleChangeEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void al(PlayerInteractEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void am(PlayerEggThrowEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void an(PlayerSpawnLocationEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ao(PlayerCommandSendEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ar(PlayerEditBookEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void as(PlayerDropItemEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void at(PlayerTakeLecternBookEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void au(PlayerInteractEntityEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void av(PlayerPickupArrowEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ay(PlayerInteractAtEntityEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void az(PlayerArmorStandManipulateEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ba(PlayerItemHeldEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bb(PlayerToggleSprintEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bd(PlayerLevelChangeEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void be(PlayerResourcePackStatusEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bf(PlayerItemBreakEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bg(PlayerRecipeDiscoverEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bh(PlayerBucketEmptyEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bi(PlayerBucketFillEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bj(PlayerChangedWorldEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void bk(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        onPlayerDoAnything(e, e.getEntity().getKiller());
    }

    @EventHandler
    public void bl(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        onPlayerDoAnything(e, e.getEntity().getKiller());
    }

    @EventHandler
    public void bi(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;
        onPlayerDoAnything(e, p);
    }

    @EventHandler
    public void cb(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        onPlayerDoAnything(e, p);
    }

    @EventHandler
    public void cc(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        onPlayerDoAnything(e, p);
    }

    @EventHandler
    public void bz(BlockBreakEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void ca(BlockPlaceEvent e) {
        onPlayerDoAnything(e, e.getPlayer());
    }

    @EventHandler
    public void cb(CraftItemEvent e) {
        onPlayerDoAnything(e, (Player) e.getWhoClicked());
    }

    @EventHandler
    public void cd(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player p) {
            onPlayerDoAnything(e, p);
        }
    }

    @EventHandler
    public void ce(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Player p) {
            onPlayerDoAnything(e, p);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCriteriaCheck(CriteriaCheckEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();

        if (e.getCriteria().shouldGiveExp(e.getMainEvent())) {
            ClassManager.addExp(p.getUniqueId(), (int) round(random() * 10));
        }
    }
}
