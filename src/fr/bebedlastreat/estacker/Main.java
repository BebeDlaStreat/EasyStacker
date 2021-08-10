package fr.bebedlastreat.estacker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        PluginManager pm = Bukkit.getPluginManager();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Main getInstance() {
        return instance;
    }
}
