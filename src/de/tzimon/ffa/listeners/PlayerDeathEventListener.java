package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.managers.ScoreboardManager;
import de.tzimon.ffa.utils.CustomPlayer;
import de.tzimon.ffa.utils.ItemBuilder;
import net.minecraft.server.v1_8_R1.EntityLightning;
import net.minecraft.server.v1_8_R1.PacketPlayOutSpawnEntityWeather;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerDeathEventListener implements Listener {

    private FFA plugin;

    private static final int REGENERATION_TIME = 10;

    private final Map<Integer, StreakReward> streakRewards;

    public PlayerDeathEventListener() {
        plugin = FFA.getPlugin();

        streakRewards = new HashMap<>();

        addRewards();
    }

    private void addRewards() {
        ItemBuilder goldenApple = new ItemBuilder(Material.GOLDEN_APPLE).setDisplayName("§eGolden Apple");

        streakRewards.put(5, new StreakReward(goldenApple));
        streakRewards.put(10, new StreakReward(goldenApple, new ItemBuilder(Material.POTION).setSubId((short) 16386)));
        streakRewards.put(25, new StreakReward(goldenApple.clone().setAmount(2), new ItemBuilder(Material.POTION).setSubId((short) 16393)));
        streakRewards.put(50, new StreakReward("§6%player% §7is a §cLegend"));
        streakRewards.put(100, new StreakReward("§6%player% §7is a §4God"));
    }

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        customPlayer.setKillStreak(0);
        ScoreboardManager.updateScoreboard(player);

        event.setKeepInventory(true);

        Player killer = player.getKiller();

        if (killer == null || player == killer) {
            player.sendMessage(plugin.prefix + "§cYou died");
        } else {
            CustomPlayer customKiller = CustomPlayer.get(killer);

            player.sendMessage(plugin.prefix + "§cYou were killed by §6" + killer.getName());
            killer.sendMessage(plugin.prefix + "§aYou killed §6" + player.getName());

            Location location = player.getLocation();
            World world = location.getWorld();
            CraftWorld craftWorld = (CraftWorld) world;

            for (Player target : world.getPlayers()) {
                CraftPlayer craftTarget = (CraftPlayer) target;
                EntityLightning lightning = new EntityLightning(craftWorld.getHandle(), location.getX(), location.getY(), location.getZ());
                PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(lightning);

                craftTarget.getHandle().playerConnection.sendPacket(packet);
            }

            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
//            killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, REGENERATION_TIME * 20, 2));
            killer.setHealth(20d);
            CustomPlayer.prepareInventory(killer, false);
            CustomPlayer.teleportToSpawn(player);

            int killStreak = customKiller.getKillStreak() + 1;
            customKiller.setKillStreak(killStreak);
            ScoreboardManager.updateScoreboard(killer);

            for (int streak : streakRewards.keySet()) {
                if (killStreak == streak)
                    streakRewards.get(streak).reward(killer, streak);
            }
        }

        CustomPlayer.preparePlayer(player);
        CustomPlayer.teleportToSpawn(player);

        event.setDeathMessage("");

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.spigot().respawn();
        }, 1);
    }

    private class StreakReward {

        private boolean messageOnly;

        private ItemBuilder[] itemBuilders;
        private String message;

        private StreakReward(ItemBuilder... itemBuilders) {
            messageOnly = false;
            this.itemBuilders = itemBuilders;
        }

        private StreakReward(String message) {
            messageOnly = true;
            this.message = message;
        }

        private void reward(Player player, int killStreak) {
            if (messageOnly)
                Bukkit.broadcastMessage(plugin.prefix + message.replace("%player%", player.getName()));
            else {
                Bukkit.broadcastMessage(plugin.prefix + "§6" + player.getName() + " §7has a KillStreak of §6" + killStreak);

                for (ItemBuilder itemBuilder : itemBuilders)
                    player.getInventory().addItem(itemBuilder.build());
            }
        }

    }

}
