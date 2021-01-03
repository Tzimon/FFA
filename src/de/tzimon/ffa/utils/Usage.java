package de.tzimon.ffa.utils;

import de.tzimon.ffa.FFA;
import org.bukkit.command.CommandSender;

public enum Usage {

    SET_HEIGHT;

    private FFA plugin;

    Usage() {
        plugin = FFA.getPlugin();
    }

    public void send(CommandSender sender) {
        sendTitle(sender);

        switch (this) {
            case SET_HEIGHT:
                sendLine(sender, "setheight death <Height>", "Sets the height below which players will die");
                sendLine(sender, "setheight game <Height>", "Sets the height below which players will be able to fight");
                break;
            default:
                sender.sendMessage(plugin.prefix + "§cNo help available");
                break;
        }
    }

    private void sendTitle(CommandSender sender) {
        sender.sendMessage(plugin.prefix + "§e§lHelp:");
    }

    private void sendLine(CommandSender sender, String usage, String description) {
        sender.sendMessage(plugin.prefix + "§8- §5/" + usage + " §8- §7" + description);
    }

}
