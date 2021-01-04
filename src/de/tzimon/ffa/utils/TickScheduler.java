package de.tzimon.ffa.utils;

import de.tzimon.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class TickScheduler {

    public static final long TIME_OF_DAY = 6000;
    public static final long TIME_RED = 3000;
    public static final long TIME_DISAPPEAR = 5000;

    private FFA plugin;

    private Map<Block, Long> blocks;

    public TickScheduler() {
        plugin = FFA.getPlugin();

        blocks = new HashMap<>();

        start();
    }

    private void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Bukkit.getWorlds().forEach(world -> {
                world.setTime(TIME_OF_DAY);
                world.setStorm(false);
                world.setThundering(false);
                world.setWeatherDuration(20);
            });

            long currentTime = System.currentTimeMillis();

            blocks.forEach((block, time) -> {
                if (currentTime >= time + TIME_DISAPPEAR) {
                    block.setType(Material.AIR);
                    return;
                }

                if (currentTime >= time + TIME_RED)
                    block.setType(Material.RED_SANDSTONE);
            });
        }, 0, 1);
    }

    public void stop() {
        blocks.keySet().forEach(block -> block.setType(Material.AIR));
        blocks.clear();
    }

    public void addBlock(Block block) {
        blocks.put(block, System.currentTimeMillis());
    }

}
