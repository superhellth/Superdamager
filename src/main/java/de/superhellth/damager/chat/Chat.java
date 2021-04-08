package de.superhellth.damager.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

    // Base color and prefix for all chat messages
    public final static ChatColor BASE_COLOR = ChatColor.DARK_GRAY;
    public final static String PREFIX = ChatColor.DARK_RED + "" +  ChatColor.BOLD + "Superdamager§r " + BASE_COLOR + "";

    /**
     * Sends a color message with prefix to the player
     * @param player Player to send message to
     * @param message Message the player receives
     */
    public static void sendMessage(Player player, String message) {
        player.sendMessage(PREFIX + message);
    }
}
