package de.superhellth.damager.main;

import de.superhellth.damager.DamagerPlugin;
import org.bukkit.Location;
import org.bukkit.Material;

public class DamagerFactory {

    /**
     * Creates a box which outlines the damager.
     * @param damager Damager to build
     */
    public static void buildDamager(Damager damager) {
        Location location = damager.getCenter();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        int radius = damager.getRadius();
        int height = DamagerPlugin.getInstance().getDamagerHeight();
        for (int dx = -radius - 1; dx <= radius + 1; dx++) {
            for (int dz = -radius - 1; dz <= radius + 1; dz++) {
                for (int dy = 0; dy <= height; dy++) {
                    Location aLoc = new Location(location.getWorld(), x + dx, y + dy, z +dz);
                    if (dy == 0 || Math.abs(dx) > radius || Math.abs(dz) > radius) {
                        aLoc.getBlock().setType(Material.BLUE_CONCRETE);
                    } else {
                        aLoc.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

}
