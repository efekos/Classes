package dev.efekos.classes.menu;

import dev.efekos.classes.Main;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu {
    public PaginatedMenu(MenuData data) {
        super(data);
    }

    protected static final int maxItemsPerPage= 28;
    private int page = 0;
    private List<ItemStack> items = new ArrayList<>();

    protected abstract List<ItemStack> setItems();

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        switch (e.getSlot()) {
            case 48 -> {
                if(page!=0){
                    page--;
                    refresh();
                }
            }
            case 50 -> {
                int maxPages = (int) Math.ceil((double) items.size() /maxItemsPerPage)-1;
                if(page<maxPages) {
                    page++;
                    e.setCancelled(true);
                    refresh();
                }
            }
        }
    }

    @Override
    public void fill() {
        this.items = setItems();
        for (Integer i:new Integer[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,51,52,53}){
            inventory.setItem(i,createItem(Material.BLACK_STAINED_GLASS_PANE," "));
        }

        inventory.setItem(48,createItem(Material.PAPER, TranslateManager.translateColors(Main.LANG.getString("menus.paginated.prev","&ePrev"))));
        inventory.setItem(49,createItem(Material.BOOK,TranslateManager.translateColors(Main.LANG.getString("menus.paginated.cur","&ePage %page%&6/&e%max%")
                .replace("%page%",page+1+"")
                .replace("%max%", Math.round((float) items.size() /maxItemsPerPage)+1 +"")
        )));
        inventory.setItem(50,createItem(Material.PAPER, TranslateManager.translateColors(Main.LANG.getString("menus.paginated.next","&eNext"))));

        int index = page*maxItemsPerPage;

        try {
            for (int i = 0; i <= 27; i++) {
                if(items.size()>i) inventory.addItem(items.get(index+i));
            }
        } catch (Exception e){
            if(e instanceof ArrayIndexOutOfBoundsException ignored) return;
            e.printStackTrace();
        }
    }
}
