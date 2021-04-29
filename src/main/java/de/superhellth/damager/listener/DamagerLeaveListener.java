package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.event.DamagerLeaveEvent;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.util.InventorySave;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

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
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getInDamager().get(player) != null) {

            leaveDamager(player, plugin.getInDamager().get(player));
            List<Entity> entities = player.getNearbyEntities(3, 2, 3);
            for (Entity entity : entities) {
                if (entity instanceof Item) {
                    entity.remove();
                }
            }
            event.setKeepInventory(true);
            event.setDeathMessage(null);
        }
    }

    private void leaveDamager(Player player, Damager damager) {
        plugin.leaveDamager(damager, player);
        int taskId = plugin.getDamageTasks().get(player);
        Bukkit.getScheduler().cancelTask(taskId);
        plugin.getDamageTasks().remove(player);
        InventorySave.readInv(player);
        player.chat("/suicide");
    }

}
