package de.tzimon.ffa;

import de.tzimon.ffa.commands.SetDeathHeightCommand;
import de.tzimon.ffa.commands.SetSpawnCommand;
import de.tzimon.ffa.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin {

    private static FFA plugin;

    public String prefix = "§6Xivar §8┃ §r";
    public String noPlayer = "§cDu musst ein Spieler sein";
    public String noPermission = "§cDu hast keine Rechte dazu";
//    public String playerNotOnline = "§cDer Spieler ist nicht online";
    public String invalidNumber = "§c%number% ist keine gültige Zahl";

    public FFA() {
        plugin = this;
    }

    public void onEnable() {
        loadConfig();
        loadListeners();
        loadCommands();
    }

    private void loadConfig() {
        getConfig().addDefault("heights.death", 0);

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new EntityDamageEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropPickupEventsListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerItemDamageEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchEventListener(), this);
    }

    private void loadCommands() {
        getCommand("setdeathheight").setExecutor(new SetDeathHeightCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
    }

    public static FFA getPlugin() {
        return plugin;
    }

}
