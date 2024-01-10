package it.mauro.dhomes.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class HomeManager {

    private final JavaPlugin plugin;

    public HomeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public File getPlayerFile(UUID uuid) {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        return new File(dataFolder, uuid + ".yml");
    }

    public YamlConfiguration getPlayerConfig(UUID uuid) {
        File playerFile = getPlayerFile(uuid);
        return YamlConfiguration.loadConfiguration(playerFile);
    }

    public void saveHomeLocation(Player player, Location location) {
        UUID uuid = player.getUniqueId();

        YamlConfiguration config = getPlayerConfig(uuid);
        config.set("home", location.serialize());

        try {
            config.save(getPlayerFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "An error occurred while saving your home.");
        }
    }

    public Location getHomeLocation(Player player) {
        UUID uuid = player.getUniqueId();

        YamlConfiguration config = getPlayerConfig(uuid);
        if (config.contains("home")) {
            return Location.deserialize(config.getConfigurationSection("home").getValues(false));
        }

        return null;
    }

    public void deleteHome(Player player) {
        UUID uuid = player.getUniqueId();

        YamlConfiguration config = getPlayerConfig(uuid);
        if (config.contains("home")) {
            config.set("home", null);

            try {
                config.save(getPlayerFile(uuid));
            } catch (IOException e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "An error occurred while deleting your home.");
            }
        }
    }
}

