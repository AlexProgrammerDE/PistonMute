package me.alexprogrammerde.pistonmute.listeners;

import me.alexprogrammerde.pistonchat.api.PistonChatEvent;
import me.alexprogrammerde.pistonmute.utils.StorageTool;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class PistonChatListener implements Listener {
    @EventHandler
    public void onChat(PistonChatEvent event) {
        if (StorageTool.isSMuted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
