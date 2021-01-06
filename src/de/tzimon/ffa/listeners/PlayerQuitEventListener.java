package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventListener implements Listener {

    @EventHandler
    public void handlePlayerQuitEvent(PlayerQuitEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(FFA.getPlugin(), ScoreboardManager::updateAll);
    }

}
