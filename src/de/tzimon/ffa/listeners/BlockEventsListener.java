package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.commands.SetHeightCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventsListener implements Listener {

    private FFA plugin;

    public BlockEventsListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handleBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        int gameHeight = plugin.getConfig().getInt("heights." + SetHeightCommand.Type.GAME.name);

        if (player.getLocation().getBlockY() < gameHeight)
            plugin.getBreakBlockScheduler().addBlock(event.getBlock());
        else
            event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
