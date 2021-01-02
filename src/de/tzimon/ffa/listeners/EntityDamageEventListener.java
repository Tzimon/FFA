package de.tzimon.ffa.listeners;

import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener {

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        CustomPlayer customPlayer = CustomPlayer.get(player);

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);

            customPlayer.preparePlayer();
            customPlayer.teleportToSpawn();
        }
    }

}
