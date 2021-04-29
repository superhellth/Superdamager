package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.main.Damager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    private final DamagerPlugin plugin;

    public ItemDropListener(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.getInDamager().get(player) != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    event.getItemDrop().remove();
                }
            },  10 * 20);
        }
    }
}
