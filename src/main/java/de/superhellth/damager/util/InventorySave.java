package de.superhellth.damager.util;

import de.superhellth.damager.DamagerPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventorySave {

    /**
     * Writes player inventory in inventories.yml.
     * @param player His inventory
     */
    public static void writeInv(Player player) {
        DamagerPlugin plugin = DamagerPlugin.getInstance();
        PlayerInventory playerInv = player.getInventory();
        plugin.getInventoryConfig().set(player.getName(), null);
        for (int i = 0; i < 4 * 9; i++) {
            try {
                plugin.getInventoryConfig().createSection(player.getName() + "." + i, playerInv.getItem(i).serialize());
            } catch (Exception ignored) {
            }
        }
        try {
            plugin.getInventoryConfig().createSection(player.getName() + ".o", playerInv.getItemInOffHand().serialize());
        } catch (NullPointerException ignored) {
        }
        try {
            plugin.getInventoryConfig().createSection(player.getName() + ".h", playerInv.getHelmet().serialize());
        } catch (NullPointerException ignored) {
        }
        try {
            plugin.getInventoryConfig().createSection(player.getName() + ".c", playerInv.getChestplate().serialize());
        } catch (NullPointerException ignored) {
        }
        try {
            plugin.getInventoryConfig().createSection(player.getName() + ".l", playerInv.getLeggings().serialize());
        } catch (NullPointerException ignored) {
        }
        try {
            plugin.getInventoryConfig().createSection(player.getName() + ".b", playerInv.getBoots().serialize());
        } catch (NullPointerException ignored) {
        }

        plugin.saveInvs();
    }

    /**
     * Fills player inventory with data from inventories.yml.
     * @param player Player to fill inventory of
     */
    public static void readInv(Player player) {
        player.getInventory().clear();
        DamagerPlugin plugin = DamagerPlugin.getInstance();
        for (int i = 0; i < 4 * 9; i++) {
            try {
                ItemStack item = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + "." + i).getValues(false));
                player.getInventory().setItem(i, item);
            } catch (Exception ignored) {
            }
        }

        try {
            ItemStack offHand = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + ".o").getValues(false));
            player.getInventory().setItemInOffHand(offHand);
        } catch (NullPointerException ignored) {
        }
        try {
            ItemStack helmet = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + ".h").getValues(false));
            player.getInventory().setHelmet(helmet);
        } catch (NullPointerException ignored) {
        }
        try {
            ItemStack chestPlate = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + ".c").getValues(false));
            player.getInventory().setChestplate(chestPlate);
        } catch (NullPointerException ignored) {
        }
        try {
            ItemStack leggings = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + ".l").getValues(false));
            player.getInventory().setLeggings(leggings);
        } catch (NullPointerException ignored) {
        }
        try {
            ItemStack boots = ItemStack.deserialize(plugin.getInventoryConfig().getConfigurationSection(player.getName() + ".b").getValues(false));
            player.getInventory().setBoots(boots);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Gives @player things needed in damager.
     * @param player To give items to
     */
    public static void getDamagerKit(Player player) {
        DamagerPlugin plugin = DamagerPlugin.getInstance();
        player.getInventory().clear();
        for (int i = 0; i < 4 * 9; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_STEW, 1));
        }
        player.getInventory().setItem(plugin.getBowlSlot(), new ItemStack(Material.BOWL, 16));
        player.getInventory().setItem(plugin.getRedMushroomSlot(), new ItemStack(Material.RED_MUSHROOM, 16));
        player.getInventory().setItem(plugin.getBrownMushroomSlot(), new ItemStack(Material.BROWN_MUSHROOM, 16));
    }

}
