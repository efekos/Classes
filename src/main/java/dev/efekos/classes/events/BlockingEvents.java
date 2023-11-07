package dev.efekos.classes.events;

import dev.efekos.classes.Main;
import dev.efekos.classes.data.Class;
import dev.efekos.classes.data.ClassManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Set;

public class BlockingEvents implements Listener {
    private boolean isBlocked(Player p, ItemStack i){
        if(i==null) return false;
        if(!ClassManager.hasClass(p.getUniqueId())) return false;
        Class clas = ClassManager.getClass(p.getUniqueId());

        Set<Enchantment> enchantments = i.getEnchantments().keySet();
        if(i.getItemMeta() instanceof PotionMeta potionMeta){
            if(potionMeta.getCustomEffects().stream().map(PotionEffect::getType).anyMatch(clas.getBlockedPotions()::contains))return true;
        }

        return clas.getBlockedMaterials().contains(i.getType()) || clas.getBlockedEnchantments().stream().anyMatch(enchantments::contains);
    }

    private boolean isBlocked(Player p, Block b){
        if(b==null)return false;
        if(!ClassManager.hasClass(p.getUniqueId())) return false;
        Class clas = ClassManager.getClass(p.getUniqueId());

        return clas.getBlockedMaterials().contains(b.getType());
    }

    @EventHandler
    public void onPlayerPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player player)) return;

        if(isBlocked(player,e.getItem().getItemStack())){
            e.setCancelled(true);
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.pickup-material","&cYour class prevents you from picking that item up.")));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(isBlocked(p,e.getItem())){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.interact-material","&cYour class prevents you from interacting with that item in your hand.")));
        } else if(isBlocked(p,e.getClickedBlock())){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.interact-material-block","&cYour class prevents you from interacting with that block.")));
        }
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        if(isBlocked(p,e.getItem())){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.consume-material","&cYour class prevents you from eating/drinking that item.")));
        }
    }

    @EventHandler
    public void onPlayerHarvest(PlayerHarvestBlockEvent e){
        Player p = e.getPlayer();
        if(isBlocked(p,e.getHarvestedBlock())){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.harvest-material","&cYour class prevents you from harvesting that block.")));
        }
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(isBlocked(p,e.getBlock())){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.break-material-block","&cYour class prevents you from breaking that block.")));
        }
    }

    @EventHandler
    public void onPlayerEnchant(EnchantItemEvent e){
        Player p = e.getEnchanter();

        if(!ClassManager.hasClass(p.getUniqueId())) return;
        Class clas = ClassManager.getClass(p.getUniqueId());

        if(e.getEnchantsToAdd().keySet().stream().anyMatch(clas.getBlockedEnchantments()::contains)) {
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.use-enchantment","&cYour class prevents you from enchanting any item with that enchantment.")));
            return;
        }


        if(isBlocked(p,e.getEnchantBlock())||isBlocked(p,e.getItem())){
            e.setCancelled(true);
            p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("blocking.enchant-material","&cYour class prevents you from enchanting that item.")));
        }
    }
}