package it.mauro.dhomes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.annotation.*;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("homeadmin")
public class HomeAdminCommand extends BaseCommand {

    private final HomeManager homeManager;

    public HomeAdminCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Default
    @Syntax("[homeadmin]")
    public void adminCommand(CommandSender sender) {
        Player player = (Player) sender;

        player.sendMessage(ChatColor.LIGHT_PURPLE+"&eHelp page");
    }

    @Subcommand("listhomes")
    @CommandPermission("sethomes.homeadmin")
    public void listHomes(CommandIssuer sender, @Name("player") Player targetPlayer) {
        Location homeLocation = homeManager.getHomeLocation(targetPlayer);

        if (homeLocation != null) {
            sender.sendMessage(ChatColor.GREEN + "Home location for " + targetPlayer.getName() + ": " +
                    "X: " + homeLocation.getX() +
                    ", Y: " + homeLocation.getY() +
                    ", Z: " + homeLocation.getZ());
        } else {
            sender.sendMessage(ChatColor.RED + targetPlayer.getName() + " hasn't set a home yet.");
        }
    }
}

