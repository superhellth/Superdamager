package de.superhellth.damager.serialization;

import de.superhellth.damager.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SerializableInventory implements ConfigurationSerializable {

    private final Inventory inventory;

    public SerializableInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public SerializableInventory(Map<String, Object> serialized) {
        inventory = Bukkit.createInventory(null, 4 * 9);
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, (ItemStack) serialized.get(i + ""));
            if (inventory.getItem(i) != null) {
                Chat.log(inventory.getItem(i).toString());
            }
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> items = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            items.put(i + "", inventory.getItem(i));
        }
        return items;
    }

    public ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }
}
