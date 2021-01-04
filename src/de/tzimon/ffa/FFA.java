package de.tzimon.ffa;

import de.tzimon.ffa.commands.BuildCommand;
import de.tzimon.ffa.commands.SetHeightCommand;
import de.tzimon.ffa.commands.SetSpawnCommand;
import de.tzimon.ffa.listeners.*;
import de.tzimon.ffa.utils.BreakBlockScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin {

    private static FFA plugin;

    private BreakBlockScheduler breakBlockScheduler;

    public String prefix = "§6Xivar §8┃ §r";
    public String noPlayer = "§cYou have to be a player";
    public String noPermission = "§cYou don't have the permissions to do that";
    public String playerNotOnline = "§cThat player is not online";
    public String invalidNumber = "§c%number% is not a valid number";

    public FFA() {
        plugin = this;
    }

    public void onEnable() {
        breakBlockScheduler = new BreakBlockScheduler();

        loadConfig();
        loadListeners();
        loadCommands();
    }

    public void onDisable() {
        breakBlockScheduler.stop();
    }

    private void loadConfig() {
        for (SetHeightCommand.Type type : SetHeightCommand.Type.values()) {
            getConfig().addDefault("heights." + type.name, type.defaultHeight);
        }

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new BlockEventsListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropPickupEventsListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerItemDamageEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchEventListener(), this);
    }

    private void loadCommands() {
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("setheight").setExecutor(new SetHeightCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
    }

    public static FFA getPlugin() {
        return plugin;
    }

    public BreakBlockScheduler getBreakBlockScheduler() {
        return breakBlockScheduler;
    }

}
