package de.superhellth.damager.command;

import de.superhellth.damager.DamagerPlugin;
import de.superhellth.damager.chat.Chat;
import de.superhellth.damager.main.Damager;
import de.superhellth.damager.main.Difficulty;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                        Chat.sendMessage(player, "/sd create <radius in blocks> <difficulty>\n"
                                                + "Type /sd difficulties for a list of difficulties!");
                    }
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

    private void listDifficulties() {
        String message = "Available difficulties:\n";
        for (Difficulty difficulty : plugin.getDifficulties()) {
            message += (difficulty.getName() + "\n");
        }
        Chat.sendMessage(player, message);
    }

    private void createDamager() throws Exception {
        Location playerLoc = player.getLocation();
        Location center = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY() - 1, playerLoc.getZ());
        int radius = Integer.parseInt(args[1]);
        Difficulty difficulty = plugin.getDifficulty(args[2]);
        Damager damager = new Damager(plugin.getFreeId(), center, radius, difficulty);
        plugin.registerDamager(damager);
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
