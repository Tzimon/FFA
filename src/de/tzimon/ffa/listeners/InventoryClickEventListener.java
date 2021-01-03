package de.tzimon.ffa.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEventListener implements Listener {

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();

        if (!(entity instanceof Player))
            return;

        event.setCancelled(true);
    }

}
