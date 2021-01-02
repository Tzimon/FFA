package de.tzimon.ffa.commands;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.Usage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetDeathHeightCommand implements CommandExecutor {

    private FFA plugin;

    public SetDeathHeightCommand() {
        plugin = FFA.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("xivar.setdeathheight")) {
            sender.sendMessage(plugin.prefix + plugin.noPermission);
            return true;
        }

        if (args.length != 1) {
            Usage.SET_DEATH_HEIGHT.send(sender);
            return true;
        }

        int height = 0;

        try {
            height = Integer.parseInt(args[0]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(plugin.prefix + plugin.invalidNumber.replace("%number%", args[0]));
            return true;
        }

        plugin.getConfig().set("heights.death", height);
        plugin.saveConfig();

        sender.sendMessage(plugin.prefix + "§7The death height was set to §6" + args[0]);
        return true;
    }

}
