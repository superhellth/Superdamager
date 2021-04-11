package de.superhellth.damager.listener;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.event.DamagerEnterEvent;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.main.Difficulty;
import de.superhellth.damager.util.InventorySave;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamagerEnterListener implements Listener {

    private DamagerPlugin plugin;

    public DamagerEnterListener(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamagerEntry(DamagerEnterEvent event) {
        Player player = event.getPlayer();
        Damager damager = event.getDamager();
        Difficulty difficulty = damager.getDifficulty();

        InventorySave.writeInv(player);
        player.setGameMode(GameMode.SURVIVAL);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 7, 10, false, false));
        player.setFoodLevel(18);
        player.setSaturation(20);
        InventorySave.getDamagerKit(player);
        plugin.enterDamager(damager, player);
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                player.damage(difficulty.getDamage());
            }
        }, 10, difficulty.getTickRate());
        plugin.getDamageTasks().put(player, taskId);
    }


}
