package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.commands.SetHeightCommand;
import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener {

    private FFA plugin;

    public EntityDamageEventListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handleEntityDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        CustomPlayer customPlayer = CustomPlayer.get(player);

        if (player.getLocation().getBlockY() > plugin.getConfig().getInt("heights." + SetHeightCommand.Type.GAME.name)) {
            event.setCancelled(true);
            return;
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);

            customPlayer.preparePlayer();
            customPlayer.teleportToSpawn();
        }
    }

}
