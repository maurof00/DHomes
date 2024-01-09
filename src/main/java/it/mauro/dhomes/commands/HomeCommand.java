package it.mauro.dhomes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CommandAlias("home")
public class HomeCommand extends BaseCommand {

    private final Map<UUID, Map<String, Location>> playerHomes = new HashMap<>();
    private final JavaPlugin plugin;

    public HomeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        loadHomes();
    }

    @Default
    @CommandPermission("home.use")
    public void onHome(Player player, @Optional String homeName) {
        if (homeName == null) {
            homeName = "default";
        }

        Location homeLocation = getHomeLocation(player, homeName);
        if (homeLocation != null) {
            player.teleport(homeLocation);
            player.sendMessage("Teleported to home: " + homeName);
        } else {
            player.sendMessage("Home " + homeName + " not found.");
        }
    }

    @Subcommand("sethome")
    @CommandPermission("home.set")
    public void onSetHome(Player player, String homeName) {
        Location location = player.getLocation();
        setHomeLocation(player, homeName, location);
        saveHomes(player.getUniqueId());
    }

    @Subcommand("delhome")
    @CommandPermission("home.delete")
    public void onDelHome(Player player, String homeName) {
        deleteHomeLocation(player, homeName);
        saveHomes(player.getUniqueId());
    }

    private Location getHomeLocation(Player player, String homeName) {
        return playerHomes.getOrDefault(player.getUniqueId(), new HashMap<>()).get(homeName);
    }

    private void setHomeLocation(Player player, String homeName, Location location) {
        playerHomes.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(homeName, location);
    }

    private void deleteHomeLocation(Player player, String homeName) {
        playerHomes.computeIfPresent(player.getUniqueId(), (k, homes) -> {
            homes.remove(homeName);
            return homes.isEmpty() ? null : homes;
        });
    }

    private void loadHomes() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File homesFile = new File(dataFolder, "homes.yml");
        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration homesConfig = YamlConfiguration.loadConfiguration(homesFile);

        for (String uuidString : homesConfig.getKeys(false)) {
            UUID uuid = UUID.fromString(uuidString);
            Map<String, Location> homes = new HashMap<>();

            for (String homeName : homesConfig.getConfigurationSection(uuidString).getKeys(false)) {
                Location homeLocation = homesConfig.getLocation(uuidString + "." + homeName);
                homes.put(homeName, homeLocation);
            }

            playerHomes.put(uuid, homes);
        }
    }

    private void saveHomes(UUID playerUUID) {
        File dataFolder = plugin.getDataFolder();
        File homesFile = new File(dataFolder, "homes.yml");

        FileConfiguration homesConfig = YamlConfiguration.loadConfiguration(homesFile);

        homesConfig.set(playerUUID.toString(), null);

        if (playerHomes.containsKey(playerUUID)) {
            for (Map.Entry<String, Location> entry : playerHomes.get(playerUUID).entrySet()) {
                homesConfig.set(playerUUID.toString() + "." + entry.getKey(), entry.getValue());
            }
        }

        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

