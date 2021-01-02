package de.tzimon.ffa.listeners;

import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchEventListener implements Listener {

    @EventHandler
    public void handleProjectileLaunchEvent(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        if (!(projectile instanceof EnderPearl))
            return;

        EnderPearl enderPearl = (EnderPearl) projectile;

        if (!(enderPearl.getShooter() instanceof Player))
            return;

        Player player = (Player) projectile.getShooter();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        customPlayer.addEnderPearl(enderPearl);
    }

}
