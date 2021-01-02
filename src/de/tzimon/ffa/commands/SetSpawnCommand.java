package de.tzimon.ffa.commands;

import de.tzimon.ffa.FFA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private FFA plugin;

    public SetSpawnCommand() {
        plugin = FFA.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + plugin.noPlayer);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("xivar.setspawn")) {
            player.sendMessage(plugin.prefix + plugin.noPermission);
            return true;
        }

        plugin.getConfig().set("locations.spawn", player.getLocation());
        plugin.saveConfig();

        player.sendMessage(plugin.prefix + "§7The spawn was set");
        return true;
    }

}
