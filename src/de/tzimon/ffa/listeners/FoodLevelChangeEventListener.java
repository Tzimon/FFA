package de.tzimon.ffa.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeEventListener implements Listener {

    @EventHandler
    public void handleFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        HumanEntity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;

        event.setCancelled(true);
        player.setFoodLevel(20);
        player.setSaturation(20);
    }

}
