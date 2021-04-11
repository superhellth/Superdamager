package de.superhellth.damager.event;

import de.superhellth.damager.main.Damager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DamagerEnterEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Damager damager;

    public DamagerEnterEvent(Player player, Damager damager) {
        this.player = player;
        this.damager = damager;
    }

    public Player getPlayer() {
        return player;
    }

    public Damager getDamager() {
        return damager;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
