package de.tzimon.ffa.listeners;

import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnEventListener implements Listener {

    @EventHandler
    public void handlePlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        customPlayer.preparePlayer();
    }

}
