package it.mauro.dhomes.commands.playercommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Syntax;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetHomeCommand extends BaseCommand {
    @Syntax("sethome")
    @CommandPermission("home.set")
    public void onSetHome(Player player, String homeName) {
        Location location = player.getLocation();
        HomeManager.setHomeLocation(player, homeName, location);
        HomeManager.saveHomes(player.getUniqueId());
        player.sendMessage("Home set.");
    }
}
