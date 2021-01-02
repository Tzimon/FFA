package de.tzimon.ffa.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomPlayer {

    private static Map<UUID, CustomPlayer> customPlayers = new HashMap<>();

    private UUID uuid;

    public static CustomPlayer get(UUID uuid) {
        if (!customPlayers.containsKey(uuid))
            customPlayers.put(uuid, new CustomPlayer(uuid));

        return customPlayers.get(uuid);
    }

    private CustomPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    private Player getPlayer() {
        Player player = Bukkit.getPlayer(uuid);

        if (player == null || !player.isOnline())
            return null;

        return player;
    }

    public void preparePlayer() {
        Player player = getPlayer();

        if (player == null)
            return;

        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setLevel(0);
        player.setExp(0);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.GOLD_SWORD).setDisplayName("§6Sword").build());
        player.getInventory().setItem(1, new ItemBuilder(Material.SNOW_BALL).setDisplayName("§fSnowball").build());
        player.getInventory().setItem(2, new ItemBuilder(Material.STICK).setDisplayName("§6Stick").build());
        player.getInventory().setItem(6, new ItemBuilder(Material.SANDSTONE).setDisplayName("§6Sandstone").build());
        player.getInventory().setItem(7, new ItemBuilder(Material.SANDSTONE).setDisplayName("§6Sandstone").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SANDSTONE).setDisplayName("§6Sandstone").build());
    }

}
