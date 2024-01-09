package it.mauro.dhomes;

import co.aikar.commands.PaperCommandManager;
import it.mauro.dhomes.commands.HomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new HomeCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
