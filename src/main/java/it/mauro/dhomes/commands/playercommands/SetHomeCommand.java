package it.mauro.dhomes.commands.playercommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("sethome")
public class SetHomeCommand extends BaseCommand {

    private final HomeManager homeManager;

    public SetHomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Default
    @CommandPermission("sethomes.sethome")
    public void setHome(Player player) {
        Location location = player.getLocation();
        homeManager.saveHomeLocation(player, location);
        player.sendMessage(ChatColor.GREEN + "Home set successfully!");
    }
}

