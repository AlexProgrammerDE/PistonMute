package net.pistonmaster.pistonmute.listeners;

import net.pistonmaster.pistonchat.api.PistonChatEvent;
import net.pistonmaster.pistonchat.api.PistonWhisperEvent;
import net.pistonmaster.pistonmute.utils.StorageTool;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class PistonChatListener implements Listener {
    @EventHandler
    public void onChat(PistonChatEvent event) {
        if (StorageTool.isMuted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(PistonWhisperEvent event) {
        if (event.getSender() == event.getReceiver()) return;

        if (event.getSender() instanceof Player && StorageTool.isMuted((Player) event.getSender())) {
            event.setCancelled(true);
        }
    }
}
