package de.tzimon.ffa.commands;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.CustomPlayer;
import de.tzimon.ffa.utils.Usage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    private FFA plugin;

    public BuildCommand() {
        plugin = FFA.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if (!sender.hasPermission("xivar.build")) {
            sender.sendMessage(plugin.prefix + plugin.noPermission);
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.prefix + plugin.noPlayer);
                return true;
            }

            player = (Player) sender;
        } else if (args.length == 1) {
            player = Bukkit.getPlayer(args[0]);

            if (player == null || !player.isOnline()) {
                sender.sendMessage(plugin.prefix + plugin.playerNotOnline);
                return true;
            }
        } else {
            Usage.BUILD.send(sender);
            return true;
        }

        CustomPlayer customPlayer = CustomPlayer.get(player);
        boolean buildMode = customPlayer.toggleBuildMode();
        String s = buildMode ? "now" : "no longer";

        player.sendMessage(plugin.prefix + "§7You are " + s + " in build mode");

        if (sender != player)
            sender.sendMessage(plugin.prefix + "§6" + player + " §7is " + s + " in build mode");

        return true;
    }

}
