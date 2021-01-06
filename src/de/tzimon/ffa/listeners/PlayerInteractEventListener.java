package de.tzimon.ffa.listeners;

import de.tzimon.ffa.FFA;
import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerInteractEventListener implements Listener {

    private FFA plugin;

    public PlayerInteractEventListener() {
        plugin = FFA.getPlugin();
    }

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!CustomPlayer.isAtSpawn(player))
            return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        int slot = player.getInventory().getHeldItemSlot();

        switch (slot) {
            case CustomPlayer.RETURN_TO_LOBBY_SLOT:
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

                try {
                    dataOutputStream.writeUTF("Connect");
                    dataOutputStream.writeUTF(FFA.LOBBY_SERVER_NAME);
                } catch (IOException ignored) {}

                player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
                break;
        }
    }

}
