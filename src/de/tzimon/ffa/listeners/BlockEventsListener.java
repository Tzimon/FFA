package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.commands.SetValueCommand;
import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
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
        CustomPlayer customPlayer = CustomPlayer.get(player);

        if (customPlayer.isBuildMode())
            return;

        if (event.getBlockReplacedState().getType() != Material.AIR) {
            event.setCancelled(true);
            return;
        }

        int gameHeight = plugin.getConfig().getInt("values." + SetValueCommand.Type.GAME.name);
        int buildLength = plugin.getConfig().getInt("values." + SetValueCommand.Type.BUILD.name);

        Location playerLocation = player.getLocation();
        boolean inGameArea = playerLocation.getX() >= -buildLength
                && playerLocation.getX() <= buildLength
                && playerLocation.getY() < gameHeight
                && playerLocation.getZ() >= -buildLength
                && playerLocation.getZ() <= buildLength;

        if (inGameArea)
            plugin.getBreakBlockScheduler().addBlock(event.getBlock());
        else
            event.setCancelled(true);
    }

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        event.setCancelled(!customPlayer.isBuildMode());
    }

}
