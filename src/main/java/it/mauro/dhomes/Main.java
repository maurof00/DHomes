package it.mauro.dhomes;

import co.aikar.commands.PaperCommandManager;
import it.mauro.dhomes.commands.HomeAdminCommand;
import it.mauro.dhomes.commands.playercommands.DeleteHomeCommand;
import it.mauro.dhomes.commands.playercommands.HomeCommand;
import it.mauro.dhomes.commands.playercommands.SetHomeCommand;
import it.mauro.dhomes.data.HomeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    private HomeManager homeManager;

    @Override
    public void onEnable() {
        instance= this;
        PaperCommandManager commandManager = new PaperCommandManager(this);
        homeManager = new HomeManager(this);

        commandManager.registerCommand(new HomeCommand(homeManager));
        commandManager.registerCommand(new DeleteHomeCommand(homeManager));
        commandManager.registerCommand(new SetHomeCommand(homeManager));
        commandManager.registerCommand(new HomeAdminCommand(homeManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}
