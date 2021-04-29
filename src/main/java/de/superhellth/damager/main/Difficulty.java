package de.superhellth.damager.main;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Difficulty implements ConfigurationSerializable {

    // attribute names
    private final static String NAME = "name";
    private final static String DAMAGE = "damage";
    private final static String TICK_RATE = "tick_rate";

    // data
    private final String name;
    private final int damage;
    private final int tickRate;

    public Difficulty(Map<String, Object> serialized) {
        name = (String) serialized.get(NAME);
        damage = (int) serialized.get(DAMAGE);
        tickRate = (int) serialized.get(TICK_RATE);
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getTickRate() {
        return tickRate;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put(NAME, name);
        serialized.put(DAMAGE, damage);
        serialized.put(TICK_RATE, tickRate);
        return serialized;
    }
}
