package de.superhellth.damager.main;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Damager implements ConfigurationSerializable {

    // attribute name
    private final static String ID = "id";
    private final static String CENTER = "center";
    private final static String RADIUS = "radius";
    private final static String DIFFICULTY = "difficulty";

    // data
    private final int id;
    private final Location center;
    private final int radius;
    private final Difficulty difficulty;

    public Damager(Map<String, Object> serialized) {
        id = (int) serialized.get(ID);
        center = (Location) serialized.get(CENTER);
        radius = (int) serialized.get(RADIUS);
        difficulty = (Difficulty) serialized.get(DIFFICULTY);
    }

    public Damager(int id, Location center, int radius, Difficulty difficulty) {
        this.id = id;
        this.center = center;
        this.radius = radius;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public Location getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put(ID, id);
        serialized.put(CENTER, center);
        serialized.put(RADIUS, radius);
        serialized.put(DIFFICULTY, difficulty);
        return serialized;
    }

    @Override
    public String toString() {
        return "Damager " + id + " : At " + center.getBlockX() + ", " + center.getBlockY() + ", " + center.getBlockZ() + "\nRadius: " + radius
                + "\nDifficulty: " + difficulty.getName();
    }
}
