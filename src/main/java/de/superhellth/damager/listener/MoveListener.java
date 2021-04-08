package de.superhellth.damager.listener;

import de.superhellth.damager.Damager;
import de.superhellth.damager.game.DamagerType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    private Damager plugin;
    private Player player;
    private boolean isInGame;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!plugin.isRunning()) {
            return;
        }
        player = event.getPlayer();
        plugin = Damager.getInstance();
        Material standingOn = player.getLocation().getBlock().getType();
        isInGame = plugin.isInGame(player);
        switch (standingOn) {
            case GREEN_CONCRETE:
                dealDamage(standingOn, DamagerType.EASY);
                break;

            case YELLOW_CONCRETE:
                dealDamage(standingOn, DamagerType.MEDIUM);
                break;

            case RED_CONCRETE:
                dealDamage(standingOn, DamagerType.HARD);
                break;

            case PURPLE_CONCRETE:
                dealDamage(standingOn, DamagerType.EXTREME);
                break;

        }
    }

    private void dealDamage(Material damageMat, DamagerType type) {
        if (isInGame && plugin.getGame(player).getBlock() != damageMat) {
            plugin.leaveGame(player);
            plugin.joinGame(player, type);
        } else {
            if (!isInGame) {
                plugin.joinGame(player, type);
            }
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                // Do something
                Player player = Bukkit.getPlayer("");
                player.damage(plugin.getGame(player).getDamage());
            }
        }, 0L, 20L);
    }

}
