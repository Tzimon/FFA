package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.managers.ScoreboardManager;
import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Score;

public class PlayerJoinEventListener implements Listener {

    private FFA plugin;

    public PlayerJoinEventListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        CustomPlayer.preparePlayer(player);
        CustomPlayer.teleportToSpawn(player);
        ScoreboardManager.createScoreboard(player);
    }

}
