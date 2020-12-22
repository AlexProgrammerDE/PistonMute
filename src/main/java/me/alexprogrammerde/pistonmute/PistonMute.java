package me.alexprogrammerde.pistonmute;

import me.alexprogrammerde.pistonmute.commands.MuteCommand;
import me.alexprogrammerde.pistonmute.commands.UnMuteCommand;
import me.alexprogrammerde.pistonmute.listeners.PistonChatListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PistonMute extends JavaPlugin {
    @Override
    public void onEnable() {
        Logger log = getLogger();

        log.info(ChatColor.YELLOW + "Registering command");
        getServer().getPluginCommand("mute").setExecutor(new MuteCommand(this));
        getServer().getPluginCommand("mute").setTabCompleter(new MuteCommand(this));

        getServer().getPluginCommand("unmute").setExecutor(new UnMuteCommand(this));
        getServer().getPluginCommand("unmute").setTabCompleter(new UnMuteCommand(this));

        log.info(ChatColor.YELLOW + "Registering chat listener");
        getServer().getPluginManager().registerEvents(new PistonChatListener(), this);
    }

    @Override
    public void onDisable() {}
}
