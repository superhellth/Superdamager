package de.superhellth.damager.util;

import de.superhellth.damager.main.Damager;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationFinder {

    /**
     * Returns the damager in the provided list which is closest to the given location.
     * @param location The given location
     * @param damagers Damagers to check
     * @return Nearest damager
     */
    public static Damager getNearest(Location location, List<Damager> damagers) {
        Map<Damager, Double> distances = new HashMap<>();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        for (Damager damager : damagers) {
            Location center = damager.getCenter();
            double dx = Math.abs(x - center.getX());
            double dy = Math.abs(y - center.getY());
            double dz = Math.abs(z - center.getZ());
            distances.put(damager, dx + dy + dz);
        }

        Damager nearest = damagers.get(0);
        for (Damager damager : damagers) {
            if (distances.get(damager) < distances.get(nearest)) {
                nearest = damager;
            }
        }

        return nearest;
    }

}
