package it.mauro.dhomes.data;

import it.mauro.dhomes.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeManager {

    private static Map<UUID, Map<String, Location>> playerHomes = new HashMap<>();

    public static Location getHomeLocation(Player player, String homeName) {
        return playerHomes.getOrDefault(player.getUniqueId(), new HashMap<>()).get(homeName);
    }

    public static void setHomeLocation(Player player, String homeName, Location location) {
        playerHomes.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(homeName, location);
    }

    public static void deleteHomeLocation(Player player, String homeName) {
        playerHomes.computeIfPresent(player.getUniqueId(), (k, homes) -> {
            homes.remove(homeName);
            return homes.isEmpty() ? null : homes;
        });
    }

    public static void loadHomes() {
        File dataFolder = Main.getInstance().getDataFolder();
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

    public static void saveHomes(UUID playerUUID) {
        File dataFolder = Main.getInstance().getDataFolder();
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
