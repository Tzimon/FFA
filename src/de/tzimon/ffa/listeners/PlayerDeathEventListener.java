package de.tzimon.ffa.listeners;

import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        event.setKeepInventory(true);

        customPlayer.preparePlayer();
        customPlayer.teleportToSpawn();
    }

}
