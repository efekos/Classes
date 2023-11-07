package dev.efekos.classes.commands.clazz;

import dev.efekos.classes.Main;
import dev.efekos.classes.Utilities;
import dev.efekos.classes.commands.arguments.ClassNameArgument;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.items.ItemContent;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@Command(name = "seticon",description = "Change icon of a class",permission = "classes.seticon",playerOnly = true)
public class SetIcon extends SubCommand {
    public SetIcon(@NotNull String name) {
        super(name);
    }

    public SetIcon(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public java.lang.Class<? extends CoreCommand> getParent() {
        return dev.efekos.classes.commands.Class.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new ClassNameArgument("class", ArgumentPriority.REQUIRED));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        dev.efekos.classes.data.Class clas = Main.getClassByName(args[0]);
        if(clas==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.generic.not-class","&cThere is no class with that name.")));
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        clas.setIcon(item);
        Main.CLASSES.update(clas.getUniqueId(),clas);

        BaseComponent component = item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).hasDisplayName() ? new TextComponent(item.getItemMeta().getDisplayName()) : TranslateManager.translateMaterial(item.getType());
        ItemStack cloned = item.clone();
        cloned.setAmount(1);
        ItemMeta meta = cloned.getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) {
            assert meta != null;
            meta.addItemFlags(flag);
        }
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,ItemContent.from(cloned) ));

        player.spigot().sendMessage(Utilities.makeComponentsForValue(TranslateManager.translateColors(
                Main.LANG.getString("commands.set-icon.done","&aSuccessfully changed &b%class%&a's icon to a(n) &f[&f%item%&f]&a!")
                        .replace("%class%",clas.getName())),"%item%",component));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender consoleCommandSender, String[] strings) {

    }
}
