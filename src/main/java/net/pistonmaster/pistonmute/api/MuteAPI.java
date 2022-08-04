package net.pistonmaster.pistonmute.api;

import net.pistonmaster.pistonmute.utils.StorageTool;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Class to interact with PistonChat!
 */
@SuppressWarnings("unused")
public final class MuteAPI {
    private MuteAPI() {
    }

    /**
     * Check if a player is muted.
     *
     * @param player The player to check
     * @return If the player is muted
     */
    public static boolean isMuted(Player player) {
        return StorageTool.isMuted(player.getUniqueId());
    }

    /**
     * Check if a player is muted.
     *
     * @param uuid The player to check
     * @return If the player is muted
     */
    public static boolean isMuted(UUID uuid) {
        return StorageTool.isMuted(uuid);
    }
}
