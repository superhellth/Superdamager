package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.event.DamagerLeaveEvent;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.serialization.SerializableInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DamagerLeaveListener implements Listener {

    private DamagerPlugin plugin;

    public DamagerLeaveListener(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamagerLeave(DamagerLeaveEvent event) {
        Player player = event.getPlayer();
        Damager damager = event.getDamager();
        plugin.leaveDamager(damager, player);
        int taskId = plugin.getDamageTasks().get(player);
        Bukkit.getScheduler().cancelTask(taskId);
        plugin.getDamageTasks().remove(player);
        for (int i = 0; i < 4 * 9; i++) {
            try {
                ItemStack item = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + "." + i).getValues(false));
                player.getInventory().setItem(i, item);
            } catch (Exception ignored) {
            }
        }
        Chat.sendMessage(player, "Damager leave");
    }

}
