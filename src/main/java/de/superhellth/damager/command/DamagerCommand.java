package de.superhellth.damager.command;

import de.superhellth.damager.Damager;
import de.superhellth.damager.chat.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DamagerCommand implements CommandExecutor {

    private Damager plugin;
    private Player player;

    public DamagerCommand(Damager plugin) {
        this.plugin = plugin;
        // Register command
        plugin.getCommand("superdamager").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        player = (Player) sender;

        if (args.length == 1) {
            String arg = args[0].toLowerCase();
            if (arg.equals("start")) {
                if (plugin.isRunning()) {
                    Chat.sendMessage(player, "The damager is already running!");
                    return true;
                }
                plugin.setRunning(true);
                Chat.sendMessage(player, "The damager has been started!");
            } else if (arg.equals("stop")) {
                if (!plugin.isRunning()) {
                    Chat.sendMessage(player, "The damager is not running!");
                    return true;
                }
                plugin.setRunning(false);
                Chat.sendMessage(player, "The damager has been stopped!");
            }
        }

        Chat.sendMessage(player, "Wrong command!");
        return true;
    }
}
