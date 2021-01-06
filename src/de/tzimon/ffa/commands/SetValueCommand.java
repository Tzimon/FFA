package de.tzimon.ffa.commands;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.Usage;
import de.tzimon.ffa.utils.Value;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetValueCommand implements CommandExecutor {

    private FFA plugin;

    public SetValueCommand() {
        plugin = FFA.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("xivar.setvalue")) {
            sender.sendMessage(plugin.prefix + plugin.noPermission);
            return true;
        }

        if (args.length != 2) {
            Usage.SET_HEIGHT.send(sender);
            return true;
        }

        int height;

        try {
            height = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(plugin.prefix + plugin.invalidNumber.replace("%number%", args[1]));
            return true;
        }

        Value value = null;

        for (Value current : Value.values()) {
            if (current.name.equalsIgnoreCase(args[0])) {
                value = current;
                break;
            }
        }

        if (value == null) {
            Usage.SET_HEIGHT.send(sender);
            return true;
        }

        plugin.getConfig().set("values." + value.name, height);
        plugin.saveConfig();

        sender.sendMessage(plugin.prefix + "§7The " + value.name + " value was set to §6" + args[1]);
        return true;
    }

}
