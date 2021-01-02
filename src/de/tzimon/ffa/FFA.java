package de.tzimon.ffa;

import de.tzimon.ffa.listeners.PlayerJoinEventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin {

    private static FFA plugin;

    public FFA() {
        plugin = this;
    }

    public void onEnable() {
        loadListeners();
    }

    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
    }

    public static FFA getPlugin() {
        return plugin;
    }

}
