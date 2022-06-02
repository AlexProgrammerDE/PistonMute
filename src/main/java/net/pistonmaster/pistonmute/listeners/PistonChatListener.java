package net.pistonmaster.pistonmute.listeners;

import lombok.RequiredArgsConstructor;
import net.pistonmaster.pistonchat.api.PistonChatEvent;
import net.pistonmaster.pistonchat.api.PistonWhisperEvent;
import net.pistonmaster.pistonchat.utils.CommonTool;
import net.pistonmaster.pistonmute.PistonMute;
import net.pistonmaster.pistonmute.utils.StorageTool;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public final class PistonChatListener implements Listener {
    private final PistonMute plugin;

    @EventHandler
    public void onChat(PistonChatEvent event) {
        Player player = event.getPlayer();

        if (StorageTool.isMuted(player.getUniqueId())) {
            if (plugin.getConfig().getBoolean("shadowMute")) {
                CommonTool.sendChatMessage(player, event.getMessage(), player);
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(PistonWhisperEvent event) {
        if (event.getSender() == event.getReceiver()) return;

        if (event.getSender() instanceof Player) {
            Player player = (Player) event.getSender();

            if (StorageTool.isHardMuted(player.getUniqueId())) {
                if (plugin.getConfig().getBoolean("shadowMute")) {
                    CommonTool.sendSender(player, event.getMessage(), event.getReceiver());
                }

                event.setCancelled(true);
            }
        }
    }
}
