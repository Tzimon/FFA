package de.tzimon.ffa.listeners;

import de.tzimon.ffa.utils.CustomPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEventListener implements Listener {

    @EventHandler
    public void handleInventoryClickEvent(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();

        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        CustomPlayer customPlayer = CustomPlayer.get(player);

        if (event.getCurrentItem() == null
                || event.getClick() == ClickType.DROP
                || event.getClick() == ClickType.CONTROL_DROP
                || event.getClick() == ClickType.WINDOW_BORDER_LEFT
                || event.getClick() == ClickType.WINDOW_BORDER_RIGHT)
            event.setCancelled(!customPlayer.isBuildMode());
    }

}
