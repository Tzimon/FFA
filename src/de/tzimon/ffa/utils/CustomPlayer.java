package de.tzimon.ffa.utils;

import de.tzimon.ffa.FFA;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class CustomPlayer {

    private static Map<UUID, CustomPlayer> customPlayers = new HashMap<>();

    public static final int RETURN_TO_LOBBY_SLOT = 8;

    private UUID uuid;

    private boolean buildMode;
    private Set<Projectile> projectiles;

    private int killStreak;

    public static CustomPlayer get(Player player) {
        return get(player.getUniqueId());
    }

    public static CustomPlayer get(UUID uuid) {
        if (!customPlayers.containsKey(uuid))
            customPlayers.put(uuid, new CustomPlayer(uuid));

        return customPlayers.get(uuid);
    }

    private CustomPlayer(UUID uuid) {
        this.uuid = uuid;
        this.projectiles = new HashSet<>();
    }

    private Player getPlayer() {
        Player player = Bukkit.getPlayer(uuid);

        if (player == null || !player.isOnline())
            return null;

        return player;
    }

    public static void preparePlayer(Player player) {
        CustomPlayer customPlayer = CustomPlayer.get(player);

        if (customPlayer.buildMode) {
            player.setGameMode(GameMode.CREATIVE);
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20d);
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setLevel(0);
            player.setExp(0);
            player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
            player.setFireTicks(0);

            customPlayer.projectiles.forEach(Projectile::remove);
            customPlayer.projectiles.clear();

            prepareInventory(player);
        }
    }

    public static void prepareInventory(Player player) {
        prepareInventory(player, true, player.getLocation().getY());
    }

    public static void prepareInventory(Player player, boolean clear) {
        prepareInventory(player, clear, player.getLocation().getY());
    }

    public static void prepareInventory(Player player, double height) {
        prepareInventory(player, true, height);
    }

    public static void prepareInventory(Player player, boolean clear, double height) {
        if (clear) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[4]);
            player.getInventory().setHeldItemSlot(0);
        }

        if (isAtSpawn(height)) {
            player.getInventory().setItem(RETURN_TO_LOBBY_SLOT,
                    new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§6Return to Lobby").build());
        } else {
            player.getInventory().setItem(0, new ItemBuilder(Material.GOLD_SWORD).setDisplayName("§6Sword")
                    .addLore("", "§eRightclick: §7Block snowballs").addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
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
    }

    public static void teleportToSpawn(Player player) {
        if (FFA.getPlugin().getConfig().contains("locations.spawn"))
            player.teleport((Location) FFA.getPlugin().getConfig().get("locations.spawn"));

        player.setVelocity(new Vector(0, 0, 0));
    }

    public boolean toggleBuildMode() {
        buildMode = !buildMode;

        Player player = getPlayer();

        if (player != null && player.isOnline())
            preparePlayer(player);

        return buildMode;
    }

    public boolean isBuildMode() {
        return buildMode;
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public static boolean isAtSpawn(Player player) {
        return isAtSpawn(player.getLocation().getY());
    }

    public static boolean isAtSpawn(double y) {
        return y > FFA.getPlugin().getConfig().getInt("values." + Value.GAME.name) + 1.5d;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public int getKillStreak() {
        return killStreak;
    }

}
