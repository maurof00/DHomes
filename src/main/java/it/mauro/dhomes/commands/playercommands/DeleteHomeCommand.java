package it.mauro.dhomes.commands.playercommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("delhome")
public class DeleteHomeCommand extends BaseCommand {

    private final HomeManager homeManager;

    public DeleteHomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Default
    @CommandPermission("sethomes.delhome")
    public void deleteHome(Player player) {
        homeManager.deleteHome(player);
        player.sendMessage(ChatColor.GREEN + "Home deleted successfully!");
    }
}

