package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {

    private FFA plugin;

    public PlayerDeathEventListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        event.setKeepInventory(true);

        Player killer = player.getKiller();

        if (killer == null || player == killer) {
            player.sendMessage(plugin.prefix + "§cYou died");
        } else {
            player.sendMessage(plugin.prefix + "§cYou were killed by §6" + killer.getName());
            killer.sendMessage(plugin.prefix + "§aYou killed §6" + player.getName());

            Location location = player.getLocation();
            World world = location.getWorld();
            world.strikeLightningEffect(location);
        }

        customPlayer.preparePlayer();
        customPlayer.teleportToSpawn();

        event.setDeathMessage("");

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.spigot().respawn();
        }, 1);
    }

}
