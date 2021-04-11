package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.event.DamagerEnterEvent;
import de.superhellth.damager.event.DamagerLeaveEvent;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.util.LocationFinder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerMovementListener implements Listener {

    private final DamagerPlugin plugin;

    public PlayerMovementListener(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntry(PlayerMoveEvent event) {
        List<Damager> allDamagers = new ArrayList<>(plugin.getDamagers().values());
        if (allDamagers.size() != 0) {
            Player player = event.getPlayer();
            Location playerLoc = player.getLocation();
            Damager damager = LocationFinder.getNearest(playerLoc, allDamagers);
            Damager playerIsIn = plugin.getInDamager().get(player);
            Location damagerLoc = damager.getCenter();
            int dx = damagerLoc.getBlockX();
            int dy = damagerLoc.getBlockY();
            int dz = damagerLoc.getBlockZ();
            int px = playerLoc.getBlockX();
            int py = playerLoc.getBlockY();
            int pz = playerLoc.getBlockZ();
            int radius = damager.getRadius();
            if (dx - radius <= px && dx + radius >= px) {
                if (dy < py && dy + plugin.getDamagerHeight() >= py) {
                    if (dz - radius <= pz && dz + radius >= pz) {
                        if (playerIsIn == null || playerIsIn.getId() != damager.getId()) {
                            Bukkit.getPluginManager().callEvent(new DamagerEnterEvent(player, damager));
                        }
                        return;
                    }
                }
            }
            if (playerIsIn != null && playerIsIn.getId() == damager.getId()) {
                Bukkit.getPluginManager().callEvent(new DamagerLeaveEvent(player, damager));
            }
        }
    }

}
