package it.mauro.dhomes.commands.playercommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import it.mauro.dhomes.data.HomeManager;
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
        HomeManager.loadHomes();
    }

    @Default
    @CommandPermission("home.use")
    public void onHome(Player player, @Optional String homeName) {
        if (homeName == null) {
            homeName = "default";
        }

        Location homeLocation = HomeManager.getHomeLocation(player, homeName);
        if (homeLocation != null) {
            player.teleport(homeLocation);
            player.sendMessage("Teleported to home: " + homeName);
        } else {
            player.sendMessage("Home " + homeName + " not found.");
        }
    }
}


