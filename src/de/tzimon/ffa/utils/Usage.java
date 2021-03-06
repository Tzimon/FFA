package de.tzimon.ffa.utils;

import de.tzimon.ffa.FFA;
import org.bukkit.command.CommandSender;

public enum Usage {

    BUILD, SET_HEIGHT;

    private FFA plugin;

    Usage() {
        plugin = FFA.getPlugin();
    }

    public void send(CommandSender sender) {
        sendTitle(sender);

        switch (this) {
            case BUILD:
                sendLine(sender, "build" , "Toggles the build mode");
                sendLine(sender, "build <Player>" , "Toggles the build mode for another player");
                break;
            case SET_HEIGHT:
                sendLine(sender, "setvalue death <Height>", "Sets the height below which players will die");
                sendLine(sender, "setvalue game <Height>", "Sets the height below which players will be able to fight");
                sendLine(sender, "setvalue build <Length>", "Sets the length of the area around the spawn where players can build");
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
