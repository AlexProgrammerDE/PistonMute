package net.pistonmaster.pistonmute;

import net.pistonmaster.pistonmute.commands.MuteCommand;
import net.pistonmaster.pistonmute.commands.UnMuteCommand;
import net.pistonmaster.pistonmute.listeners.PistonChatListener;
import net.pistonmaster.pistonmute.utils.StorageTool;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PistonMute extends JavaPlugin {
    @Override
    public void onEnable() {
        Logger log = getLogger();

        log.info(ChatColor.YELLOW + "Loading config");
        StorageTool.setupTool(this);

        log.info(ChatColor.YELLOW + "Registering command");
        getServer().getPluginCommand("mute").setExecutor(new MuteCommand(this));
        getServer().getPluginCommand("mute").setTabCompleter(new MuteCommand(this));

        getServer().getPluginCommand("unmute").setExecutor(new UnMuteCommand(this));
        getServer().getPluginCommand("unmute").setTabCompleter(new UnMuteCommand(this));

        log.info(ChatColor.YELLOW + "Registering chat listener");
        getServer().getPluginManager().registerEvents(new PistonChatListener(), this);
    }
}
