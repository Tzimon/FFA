package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.CustomPlayer;
import de.tzimon.ffa.utils.Value;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEventListener implements Listener {

    private FFA plugin;

    public PlayerMoveEventListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handlePlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        if (customPlayer.isBuildMode())
            return;

        if (CustomPlayer.isAtSpawn(event.getFrom().getY()) && !CustomPlayer.isAtSpawn(event.getTo().getY()))
            CustomPlayer.prepareInventory(player, event.getTo().getY());

        if (player.getLocation().getBlockY() <= plugin.getConfig().getInt("heights." + Value.DEATH.name))
            player.setHealth(0);
    }

}
