package it.mauro.dhomes;

import co.aikar.commands.PaperCommandManager;
import it.mauro.dhomes.commands.playercommands.DeleteHomeCommand;
import it.mauro.dhomes.commands.playercommands.HomeCommand;
import it.mauro.dhomes.commands.playercommands.SetHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance= this;
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new HomeCommand(this));
        commandManager.registerCommand(new DeleteHomeCommand());
        commandManager.registerCommand(new SetHomeCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}
