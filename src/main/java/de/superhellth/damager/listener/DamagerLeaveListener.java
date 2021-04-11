package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.event.DamagerLeaveEvent;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.util.InventorySave;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DamagerLeaveListener implements Listener {

    private DamagerPlugin plugin;

    public DamagerLeaveListener(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamagerLeave(DamagerLeaveEvent event) {
        Player player = event.getPlayer();
        Damager damager = event.getDamager();
        leaveDamager(player, damager);
    }

    @EventHandler
    public void onDamager(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getInDamager().get(player) != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Event respawn = new PlayerRespawnEvent(player, Bukkit.getServer().getWorld(player.getLocation().getWorld().getUID()).getSpawnLocation() ,
                            true, false);
                    Bukkit.getPluginManager().callEvent(respawn);
                    leaveDamager(player, plugin.getInDamager().get(player));
                }
            }, 10);
            event.setDeathMessage(null);
        }
    }

    private void leaveDamager(Player player, Damager damager) {
        plugin.leaveDamager(damager, player);
        int taskId = plugin.getDamageTasks().get(player);
        Bukkit.getScheduler().cancelTask(taskId);
        plugin.getDamageTasks().remove(player);
        InventorySave.readInv(player);
    }

}
