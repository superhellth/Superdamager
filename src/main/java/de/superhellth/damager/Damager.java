package de.superhellth.damager;

import de.superhellth.damager.game.DamagerGame;
import de.superhellth.damager.game.DamagerType;
import de.superhellth.damager.listener.MoveListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Damager extends JavaPlugin {

    // singleton pattern
    private static Damager instance;
    public static Damager getInstance() {
        if (instance == null) {
            instance = new Damager();
        }
        return instance;
    }

    private boolean isRunning;
    private final Map<DamagerGame, Set<Player>> games = new HashMap<>();
    private final Map<DamagerType, DamagerGame> gameDifs = new HashMap<>();

    @Override
    public void onEnable() {
        // Register listener
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        // Start games for all difficulties
        for (DamagerType type : DamagerType.values()) {
            DamagerGame game = new DamagerGame(type);
            games.put(game, new HashSet<>());
            gameDifs.put(type, game);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Checks if damager plugin is running.
     * @return Whether the plugin is running or not
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Starts/stops damager.
     * @param running value to set to
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * Check if player is in game.
     * @param player to check
     * @return Bool if is in game
     */
    public boolean isInGame(Player player) {
        return getGame(player) != null;
    }

    /**
     * Get game player is in
     * @param player to check
     * @return The game
     */
    public DamagerGame getGame(Player player) {
        for (DamagerGame game : games.keySet()) {
            if (games.get(game).contains(player)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Adds player to the game with the given difficulty
     * @param player to add
     * @param type game difficulty
     */
    public void joinGame(Player player, DamagerType type) {
        games.get(gameDifs.get(type)).add(player);
    }

    /**
     * Removes player from the game with the given difficulty
     * @param player to add
     */
    public void leaveGame(Player player) {
        games.get(getGame(player)).remove(player);
    }
}
