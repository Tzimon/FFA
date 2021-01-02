package de.tzimon.ffa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamageEventListener implements Listener {

    @EventHandler
    public void handlePlayerItemDamageEvent(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);
        player.updateInventory();
    }

}
