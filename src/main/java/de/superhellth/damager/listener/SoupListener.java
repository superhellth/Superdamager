package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SoupListener implements Listener {

    private final DamagerPlugin plugin;

    public SoupListener(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSoup(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getInDamager().get(player) != null) {
            if (event.getItem() != null) {
                if (event.getItem().getType() == Material.MUSHROOM_STEW) {
                    if (player.getHealth() != 20) {
                        int max_health = (int) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                        int heal = player.getHealth() < max_health - plugin.getSoupHeal() ? (int) player.getHealth() + plugin.getSoupHeal() : max_health;
                        player.setHealth(heal);
                        player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL, 1));
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
