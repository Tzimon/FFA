package de.tzimon.ffa.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerDropPickupEventsListener implements Listener {

    @EventHandler
    public void handlePlayerDropItemEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerPickupItemEvent(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

}
