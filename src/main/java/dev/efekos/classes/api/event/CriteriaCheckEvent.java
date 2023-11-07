package dev.efekos.classes.api.event;

import dev.efekos.classes.api.i.ILevelCriteria;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class CriteriaCheckEvent extends Event implements Cancellable {
    public static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final ILevelCriteria criteria;
    private final Event mainEvent;


    public CriteriaCheckEvent(Player player, ILevelCriteria criteria, Event mainEvent) {
        this.player = player;
        this.criteria = criteria;
        this.mainEvent = mainEvent;
    }

    public CriteriaCheckEvent(boolean isAsync, Player player, ILevelCriteria criteria, Event mainEvent) {
        super(isAsync);
        this.player = player;
        this.criteria = criteria;
        this.mainEvent = mainEvent;
    }

    public Player getPlayer() {
        return player;
    }

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public ILevelCriteria getCriteria() {
        return criteria;
    }

    public Event getMainEvent() {
        return mainEvent;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
