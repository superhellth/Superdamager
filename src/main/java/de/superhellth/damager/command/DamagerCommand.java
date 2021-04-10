package de.superhellth.damager.command;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.main.DamagerFactory;
import de.superhellth.damager.main.Difficulty;
import de.superhellth.damager.util.LocationFinder;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DamagerCommand implements CommandExecutor {

    private final DamagerPlugin plugin;
    private Player player;
    private String[] args;

    public DamagerCommand(DamagerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.args = args;
        if (wrongInputs(sender)) {
            return true;
        }
        player = (Player) sender;

        switch (args[0]) {
            case "create":
                if (hasCorrectArgs(3, Allow.ONLY_EQUAL)) {
                    try {
                        createDamager();
                    } catch (Exception e) {
                        Chat.sendMessage(player, "/sd create <difficulty> <radius in blocks>\n"
                                                + "Type /sd difficulties for a list of difficulties!");
                    }
                }
                break;

            case "delete":
                if (hasCorrectArgs(1, Allow.ONLY_EQUAL)) {
                    deleteNearest();
                }
                break;

            case "damagers":
                if (hasCorrectArgs(1, Allow.ONLY_EQUAL)) {
                    listDamagers();
                }
                break;

            case "difficulties":
                if (hasCorrectArgs(2, Allow.ONLY_EQUAL)) {
                    listDifficulties();
                }
                break;

            default:
                Chat.sendMessage(player, "Unknown command!");
        }

        return true;
    }

    private void createDamager() throws NullPointerException {
        Location playerLoc = player.getLocation();
        Location center = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY() - 1, playerLoc.getZ());
        Difficulty difficulty = plugin.getDifficulty(args[1]);
        if (difficulty == null) {
            throw new NullPointerException();
        }
        int radius = Integer.parseInt(args[2]);
        Damager damager = new Damager(plugin.getFreeId(), center, radius, difficulty);
        plugin.registerDamager(damager);
        DamagerFactory.buildDamager(damager);
        Chat.sendMessage(player, "Damager has been created!");
    }

    private void deleteNearest() {
        Damager damager = LocationFinder.getNearest(player.getLocation(), new ArrayList<>(plugin.getDamagers().values()));
        plugin.removeDamager(damager);
        Chat.sendMessage(player, "Damager has been deleted!");
    }

    private void listDamagers() {
        String message = "Damagers:\n";
        for (Damager damager : plugin.getDamagers().values()) {
            message += damager.toString() + "\n";
        }
        Chat.sendMessage(player, message);
    }

    private void listDifficulties() {
        String message = "Available difficulties:\n";
        for (Difficulty difficulty : plugin.getDifficulties()) {
            message += (difficulty.getName() + "\n");
        }
        Chat.sendMessage(player, message);
    }

    private boolean wrongInputs(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Chat.log("Only players can execute this command!");
            return true;
        } else if (!hasCorrectArgs(1, Allow.OR_MORE)) {
            return true;
        }
        return false;
    }

    private boolean hasCorrectArgs(int numArgs, Allow option) {
        int givenArgs = args.length;
        switch (option) {
            case OR_LESS:
                if (givenArgs == numArgs || givenArgs < numArgs) {
                    return true;
                }
                Chat.sendMessage(player, "Too many arguments!");
                break;

            case ONLY_EQUAL:
                if (givenArgs == numArgs) {
                    return true;
                }
                Chat.sendMessage(player, "Wrong amount of arguments!");
                break;

            case OR_MORE:
                if (givenArgs == numArgs || givenArgs > numArgs) {
                    return true;
                }
                Chat.sendMessage(player, "Not enough arguments!");
                break;
        }
        return false;
    }
}
