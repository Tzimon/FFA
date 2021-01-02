package de.tzimon.ffa.utils;

import de.tzimon.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;

import java.util.*;

public class CustomPlayer {

    private static Map<UUID, CustomPlayer> customPlayers = new HashMap<>();

    private FFA plugin;

    private UUID uuid;

    private Set<EnderPearl> enderPearls;

    public static CustomPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public static CustomPlayer get(UUID uuid) {
        if (!customPlayers.containsKey(uuid))
            customPlayers.put(uuid, new CustomPlayer(uuid));

        return customPlayers.get(uuid);
    }

    private CustomPlayer(UUID uuid) {
        plugin = FFA.getPlugin();

        this.uuid = uuid;
        this.enderPearls = new HashSet<>();
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
        player.setHealth(20d);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setLevel(0);
        player.setExp(0);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        enderPearls.forEach(EnderPearl::remove);
        enderPearls.clear();

        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.GOLD_SWORD).setDisplayName("§6Sword")
                .addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
        player.getInventory().setItem(1, new ItemBuilder(Material.SNOW_BALL).setDisplayName("§6Snowball").setAmount(16).build());
        player.getInventory().setItem(2, new ItemBuilder(Material.STICK).setDisplayName("§6Stick")
                .addEnchantment(Enchantment.KNOCKBACK, 1).build());
        player.getInventory().setItem(4, new ItemBuilder(Material.ENDER_PEARL).setDisplayName("§6Pearl").build());
        player.getInventory().setItem(6, new ItemBuilder(Material.SANDSTONE).setDisplayName("§6Sandstone").setAmount(64).build());
        player.getInventory().setItem(7, new ItemBuilder(Material.SANDSTONE).setDisplayName("§6Sandstone").setAmount(64).build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SANDSTONE).setDisplayName("§6Sandstone").setAmount(64).build());
        player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setDisplayName("§6Boots")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        player.getInventory().setLeggings(new ItemBuilder(Material.GOLD_LEGGINGS).setDisplayName("§6Leggings")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        player.getInventory().setChestplate(new ItemBuilder(Material.GOLD_CHESTPLATE).setDisplayName("§6Chestplate")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
    }

    public void teleportToSpawn() {
        Player player = getPlayer();

        if (player == null)
            return;

        if (plugin.getConfig().contains("locations.spawn"))
            player.teleport((Location) plugin.getConfig().get("locations.spawn"));
    }

    public void addEnderPearl(EnderPearl enderPearl) {
        enderPearls.add(enderPearl);
    }

}
