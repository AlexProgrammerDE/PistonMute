package net.pistonmaster.pistonmute.commands;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.pistonmaster.pistonmute.PistonMute;
import net.pistonmaster.pistonmute.utils.MojangUtil;
import net.pistonmaster.pistonmute.utils.StorageTool;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public final class UnMuteCommand implements CommandExecutor, TabExecutor {
    private final PistonMute plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            @Nullable UUID uuid = null;
            @Nullable UUID senderUUID = null;
            @Nullable String name = null;

            if (sender instanceof Player) {
                senderUUID = ((Player) sender).getUniqueId();
            }

            if (args[0].matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
                name = MojangUtil.getName(args[0]);
                uuid = UUID.fromString(args[0]);
            } else {
                Player player = plugin.getServer().getPlayer(args[0]);

                if (player != null) {
                    name = player.getName();
                    uuid = player.getUniqueId();
                }
            }

            if (uuid != null) {
                if (uuid != senderUUID || sender instanceof ConsoleCommandSender) {
                    if (StorageTool.unMutePlayer(uuid)) {
                        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                        sender.spigot().sendMessage(new ComponentBuilder("PistonMute").color(ChatColor.GOLD).create());
                        sender.spigot().sendMessage(new ComponentBuilder("Successfully unmuted " + name + "!").color(ChatColor.GREEN).create());
                        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                    } else {
                        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                        sender.spigot().sendMessage(new ComponentBuilder("PistonMute").color(ChatColor.GOLD).create());
                        sender.spigot().sendMessage(new ComponentBuilder(name + " wasn't muted!").color(ChatColor.RED).create());
                        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                    }
                } else {
                    sender.sendMessage("Please don't mute yourself!");
                }
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                players.add(player.getName());
            }

            List<String> completions = new ArrayList<>();

            StringUtil.copyPartialMatches(args[0], players, completions);

            Collections.sort(completions);

            return completions;
        } else {
            return new ArrayList<>();
        }
    }
}
