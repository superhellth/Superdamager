package de.superhellth.damager.game;

import de.superhellth.damager.Damager;
import org.bukkit.Material;

public class DamagerGame {

    private DamagerType type;

    public DamagerGame(DamagerType type) {
        this.type = type;
    }

    public Material getBlock() {
        switch (type) {
            case EASY:
                return Material.GREEN_CONCRETE;

            case MEDIUM:
                return Material.YELLOW_CONCRETE;

            case HARD:
                return Material.RED_CONCRETE;

            case EXTREME:
                return Material.PURPLE_CONCRETE;
        }

        return Material.BEDROCK;
    }

    public int getDamage() {
        switch (type) {
            case EASY:
                return 3;

            case MEDIUM:
                return 5;

            case HARD:
                return 6;

            case EXTREME:
                return 10;
        }

        return 0;
    }

}
