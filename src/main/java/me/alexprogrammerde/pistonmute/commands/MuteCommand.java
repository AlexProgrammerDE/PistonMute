package me.alexprogrammerde.pistonmute.commands;

import me.alexprogrammerde.pistonmute.PistonMute;
import me.alexprogrammerde.pistonmute.utils.StorageTool;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public final class MuteCommand implements CommandExecutor, TabExecutor {
    private final PistonMute plugin;

    public MuteCommand(PistonMute plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args.length > 1) {
                Player player = plugin.getServer().getPlayer(args[0]);

                if (player != null) {
                    if (player != sender) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());

                        if (args[2].toLowerCase().endsWith("d")) {
                            int d = Integer.parseInt(args[2].toLowerCase().replaceAll("d", ""));

                            calendar.add(Calendar.DAY_OF_WEEK, d);
                        } else if (args[2].toLowerCase().endsWith("h")) {
                            int h = Integer.parseInt(args[2].toLowerCase().replaceAll("h", ""));

                            calendar.add(Calendar.HOUR_OF_DAY, h);
                        } else if (args[2].toLowerCase().endsWith("m")) {
                            int m = Integer.parseInt(args[2].toLowerCase().replaceAll("m", ""));

                            calendar.add(Calendar.MINUTE, m);
                        } else if (args[2].toLowerCase().endsWith("s")) {
                            int s = Integer.parseInt(args[2].toLowerCase().replaceAll("s", ""));

                            calendar.add(Calendar.SECOND, s);
                        } else {
                            return false;
                        }

                        if (StorageTool.tempMutePlayer(player, calendar.getTime())) {
                            sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                            sender.spigot().sendMessage(new ComponentBuilder("PistonMute").color(ChatColor.GOLD).create());
                            sender.spigot().sendMessage(new ComponentBuilder("Successfully muted " + player.getName() + "!").color(ChatColor.GREEN).create());
                            sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                        } else {
                            sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                            sender.spigot().sendMessage(new ComponentBuilder("PistonMute").color(ChatColor.GOLD).create());
                            sender.spigot().sendMessage(new ComponentBuilder(player.getName() + " is already muted!").color(ChatColor.RED).create());
                            sender.spigot().sendMessage(new ComponentBuilder("----------------").color(ChatColor.DARK_BLUE).create());
                        }
                    } else {
                        sender.sendMessage("Please don't mute yourself!");
                    }
                } else {
                    return false;
                }
            } else {
                return false;
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
