package de.superhellth.damager;

import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.command.DamagerCommand;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.main.Difficulty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class DamagerPlugin extends JavaPlugin {

    // file and directory names
    private final static String DAMAGER_FILE = "damager.yml";
    private final static String DIFFICULTY_FILE = "difficulties.yml";
    // singleton pattern
    private static DamagerPlugin instance;

    // data files
    private final File damagerFile = new File(getDataFolder(), DAMAGER_FILE);
    private final File difficultyFile = new File(getDataFolder(), DIFFICULTY_FILE);
    private final File configFile = new File(getDataFolder(), "config.yml");
    private YamlConfiguration damagerConfig;
    private YamlConfiguration difficultyConfig;

    // data
    private final Set<Difficulty> difficulties = new HashSet<>();
    private final Map<Integer, Damager> damagers = new HashMap<>();

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

        // Register command
        this.getCommand("superdamager").setExecutor(new DamagerCommand(this));

        // Load config
        loadConfig();
        damagerConfig = YamlConfiguration.loadConfiguration(damagerFile);
        difficultyConfig = YamlConfiguration.loadConfiguration(difficultyFile);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerDamager(Damager damager) {
        damagerConfig.createSection("damage_" + damager.getId(), damager.serialize());
        try {
            damagerConfig.save(damagerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getter
    public Difficulty getDifficulty(String name) {
        for (Difficulty difficulty : difficulties) {
            if (difficulty.getName().toLowerCase().equals(name)) {
                return difficulty;
            }
        }
        return null;
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

    public Set<Difficulty> getDifficulties() {
        return difficulties;
    }

    // loading config
    private void loadConfig() {
        if (!configFile.exists()) {
            createPluginDirectory();
        }
        loadDifficulties();
        loadDamagers();
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

    }

    private void createPluginDirectory() {
        saveDefaultConfig();
        try {
            damagerFile.createNewFile();
            difficultyFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
