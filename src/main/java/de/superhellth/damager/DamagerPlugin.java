package de.superhellth.damager;

import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.command.DamagerCommand;
import de.superhellth.damager.listener.*;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.main.Difficulty;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DamagerPlugin extends JavaPlugin {

    // file and directory names
    private final static String DAMAGER_FILE = "damagers.yml";
    private final static String DIFFICULTY_FILE = "difficulties.yml";
    private final static String INVENTORY_FILE = "inventories.yml";
    // singleton pattern
    private static DamagerPlugin instance;

    // data files
    private final File damagerFile = new File(getDataFolder(), DAMAGER_FILE);
    private final File difficultyFile = new File(getDataFolder(), DIFFICULTY_FILE);
    private final File inventoryFile = new File(getDataFolder(), INVENTORY_FILE);
    private final File configFile = new File(getDataFolder(), "config.yml");
    private YamlConfiguration damagerConfig;
    private YamlConfiguration difficultyConfig;
    private YamlConfiguration inventoryConfig;

    // data
    private final Set<Difficulty> difficulties = new HashSet<>();
    private final Map<Integer, Damager> damagers = new HashMap<>();
    private final Map<Player, Damager> inDamager = new HashMap<>();
    private final Map<Player, Integer> damageTasks = new HashMap<>();
    private int damagerHeight;
    private int soupHeal;

    /**
     * Returns the current instance of the plugin.
     * @return Returns the instance
     */
    public static DamagerPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // register serializable
        ConfigurationSerialization.registerClass(Difficulty.class);
        ConfigurationSerialization.registerClass(Damager.class);

        // Register command
        this.getCommand("superdamager").setExecutor(new DamagerCommand(this));

        // Register listener
        Bukkit.getPluginManager().registerEvents(new PlayerMovementListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DamagerEnterListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DamagerLeaveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemDropListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SoupListener(this), this);

        // Load config
        damagerConfig = YamlConfiguration.loadConfiguration(damagerFile);
        difficultyConfig = YamlConfiguration.loadConfiguration(difficultyFile);
        inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
        loadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // getter
    public YamlConfiguration getInventoryConfig() {
        return inventoryConfig;
    }

    public Set<Difficulty> getDifficulties() {
        return difficulties;
    }

    public Difficulty getDifficulty(String name) {
        for (Difficulty difficulty : difficulties) {
            if (difficulty.getName().toLowerCase().equals(name)) {
                return difficulty;
            }
        }
        return null;
    }

    public Map<Integer, Damager> getDamagers() {
        return damagers;
    }

    public int getDamagerHeight() {
        return damagerHeight;
    }

    public int getSoupHeal() {
        return soupHeal;
    }

    public int getFreeId() {
        Set<Integer> ids = new HashSet<>();
        for (Damager damager : damagers.values()) {
            ids.add(damager.getId());
        }
        int i;
        for (i = 0; i < ids.size(); i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }
        return i;
    }

    public Map<Player, Damager> getInDamager() {
        return inDamager;
    }

    public Map<Player, Integer> getDamageTasks() {
        return damageTasks;
    }

    public void enterDamager(Damager damager, Player player) {
        inDamager.put(player, damager);
    }

    public void leaveDamager(Damager damager, Player player) {
        inDamager.remove(player);
    }

    // Damager management
    /**
     * Registers the given damager.
     * @param damager Damager to register
     */
    public void registerDamager(Damager damager) {
        damagerConfig.createSection("damager_" + damager.getId(), damager.serialize());
        damagers.put(damager.getId(), damager);
        try {
            damagerConfig.save(damagerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the given damager.
     * @param damager Damager to remove
     */
    public void removeDamager(Damager damager) {
        damagerConfig.set("damager_" + damager.getId(), null);
        damagers.remove(damager.getId());
        try {
            damagerConfig.save(damagerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInvs() {
        try {
            inventoryConfig.save(inventoryFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loading config
    private void loadConfig() {
        if (!configFile.exists()) {
            createPluginDirectory();
        }
        loadDifficulties();
        loadDamagers();
        damagerHeight = getConfig().getInt("damager_height");
        soupHeal = getConfig().getInt("soup_heal");
    }

    private void loadDifficulties() {
        int i = 1;
        while (true) {
            try {
                Difficulty difficulty = new Difficulty(difficultyConfig.getConfigurationSection("difficulty_" + i).getValues(false));
                difficulties.add(difficulty);
            } catch (NullPointerException e) {
                break;
            }
            i++;
        }
    }

    private void loadDamagers() {
        for (int i = 0; i < 50; i++) {
            try {
                Damager damager = new Damager(damagerConfig.getConfigurationSection("damager_" + i).getValues(false));
                damagers.put(i, damager);
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void createPluginDirectory() {
        saveDefaultConfig();
        try {
            damagerFile.createNewFile();
            difficultyFile.createNewFile();
            inventoryFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
