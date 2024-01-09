package it.mauro.dhomes.commands.playercommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DeleteHomeCommand extends BaseCommand {

    @Default
    @Syntax("delhome")
    @CommandPermission("home.delete")
    public void onDelHome(Player player, String homeName) {
        HomeManager.deleteHomeLocation(player, homeName);
        HomeManager.saveHomes(player.getUniqueId());
        player.sendMessage("Home deleted.");
    }

}
