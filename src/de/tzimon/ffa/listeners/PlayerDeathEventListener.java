package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.CustomPlayer;
import net.minecraft.server.v1_8_R1.EntityLightning;
import net.minecraft.server.v1_8_R1.PacketPlayOutSpawnEntityWeather;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathEventListener implements Listener {

    private FFA plugin;

    private static final int REGENERATION_TIME = 10;

    public PlayerDeathEventListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handlePlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CustomPlayer customPlayer = CustomPlayer.get(player);

        customPlayer.setKillStreak(0);

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
            CustomPlayer.prepareInventory(killer);

            int killStreak = customKiller.getKillStreak() + 1;
            customKiller.setKillStreak(killStreak);

            if (killStreak == 5) {
                Bukkit.broadcastMessage(plugin.prefix + "§6" + killer.getName() + " §7hat eine KillStreak von §6" + killStreak);
            }
        }

        CustomPlayer.preparePlayer(player);
        CustomPlayer.teleportToSpawn(player);

        event.setDeathMessage("");

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.spigot().respawn();
        }, 1);
    }

}
