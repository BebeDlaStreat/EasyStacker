package fr.bebedlastreat.estacker;

import fr.bebedlastreat.estacker.listeners.BlockListener;
import fr.bebedlastreat.estacker.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
    private static Main instance;

    private List<Material> stackingBlocks;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        stackingBlocks = ConfigManager.getStackingBlocks();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockListener(this), this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Main getInstance() {
        return instance;
    }

    public List<Material> getStackingBlocks() {
        return stackingBlocks;
    }
}
