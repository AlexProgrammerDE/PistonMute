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
import java.util.*;

@RequiredArgsConstructor
public final class MuteCommand implements CommandExecutor, TabExecutor {
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
                uuid = UUID.fromString(args[0]);
                name = MojangUtil.getName(uuid);
            } else {
                Player player = plugin.getServer().getPlayer(args[0]);

                if (player != null) {
                    name = player.getName();
                    uuid = player.getUniqueId();
                }
            }

            if (uuid != null) {
                if (uuid != senderUUID || sender instanceof ConsoleCommandSender) {
                    if (args.length > 1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());

                        if (args[1].toLowerCase().endsWith("y")) {
                            int d = Integer.parseInt(args[1].toLowerCase().replace("y", ""));

                            calendar.add(Calendar.YEAR, d);
                        } else if (args[1].toLowerCase().endsWith("d")) {
                            int d = Integer.parseInt(args[1].toLowerCase().replace("d", ""));

                            calendar.add(Calendar.DAY_OF_WEEK, d);
                        } else if (args[1].toLowerCase().endsWith("h")) {
                            int h = Integer.parseInt(args[1].toLowerCase().replace("h", ""));

                            calendar.add(Calendar.HOUR_OF_DAY, h);
                        } else if (args[1].toLowerCase().endsWith("m")) {
                            int m = Integer.parseInt(args[1].toLowerCase().replace("m", ""));

                            calendar.add(Calendar.MINUTE, m);
                        } else if (args[1].toLowerCase().endsWith("s")) {
                            int s = Integer.parseInt(args[1].toLowerCase().replace("s", ""));

                            calendar.add(Calendar.SECOND, s);
                        } else {
                            return false;
                        }

                        if (StorageTool.tempMutePlayer(uuid, calendar.getTime())) {
                            successMessage(sender, name);
                        } else {
                            alreadyMutedMessage(sender, name);
                        }
                    } else {
                        if (StorageTool.hardMutePlayer(uuid)) {
                            successMessage(sender, name);
                        } else {
                            alreadyMutedMessage(sender, name);
                        }
                    }
                } else {
                    sender.sendMessage("You can't mute yourself!");
                }
            } else {
                sender.sendMessage("Could not mute player!");
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    private void alreadyMutedMessage(CommandSender sender, String name) {
        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
        sender.spigot().sendMessage(new ComponentBuilder("PistonMute").color(ChatColor.GOLD).create());
        sender.spigot().sendMessage(new ComponentBuilder(name + " is already muted!").color(ChatColor.RED).create());
        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
    }

    private void successMessage(CommandSender sender, String name) {
        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
        sender.spigot().sendMessage(new ComponentBuilder("PistonMute").color(ChatColor.GOLD).create());
        sender.spigot().sendMessage(new ComponentBuilder("Successfully muted " + name + "!").color(ChatColor.GREEN).create());
        sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
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
