package it.mauro.dhomes.commands.playercommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("home")
public class HomeCommand extends BaseCommand {

    private final HomeManager homeManager;

    public HomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Default
    @CommandPermission("sethomes.home")
    public void goHome(Player player) {
        Location homeLocation = homeManager.getHomeLocation(player);

        if (homeLocation != null) {
            player.teleport(homeLocation);
            player.sendMessage(ChatColor.GREEN + "Teleported to home!");
        } else {
            player.sendMessage(ChatColor.RED + "You haven't set a home yet. Use /sethome to set one.");
        }
    }
}



