package net.pistonmaster.pistonmute.utils;

import net.pistonmaster.pistonmute.PistonMute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class StorageTool {
    private static PistonMute plugin;
    private static FileConfiguration dataConfig;
    private static File dataFile;

    /**
     * Mute a player!
     *
     * @param player The player to shadowban.
     * @param date   The date when he will be unbanned.
     * @return true if player got muted and if already muted false.
     */
    public static boolean tempMutePlayer(Player player, Date date) {
        manageMute(player);

        if (!dataConfig.contains(player.getUniqueId().toString())) {
            dataConfig.set(player.getUniqueId().toString(), date.toString());

            saveData();

            return true;
        } else {
            return false;
        }
    }

    /**
     * Unmute a player!
     *
     * @param player The player to unmute.
     * @return true if player got unmuted and false if not was muted.
     */
    public static boolean unMutePlayer(Player player) {
        if (dataConfig.contains(player.getUniqueId().toString())) {
            dataConfig.set(player.getUniqueId().toString(), null);

            saveData();

            return true;
        } else {
            return false;
        }
    }

    public static boolean isMuted(Player player) {
        manageMute(player);

        return dataConfig.contains(player.getUniqueId().toString());
    }

    private static void manageMute(Player player) {
        Date now = new Date();

        if (dataConfig.contains(player.getUniqueId().toString())) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));

            try {
                Date date = sdf.parse(dataConfig.getString(player.getUniqueId().toString()));

                if (now.after(date) || (now.equals(date))) {
                    unMutePlayer(player);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadData() {
        generateFile();

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    private static void saveData() {
        generateFile();

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateFile() {
        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdir())
                return;
        }

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupTool(PistonMute plugin) {
        StorageTool.plugin = plugin;
        StorageTool.dataFile = new File(plugin.getDataFolder(), "data.yml");

        loadData();
    }
}
