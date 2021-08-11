package fr.bebedlastreat.estacker;

import fr.bebedlastreat.estacker.listeners.BlockListener;
import fr.bebedlastreat.estacker.utils.ConfigManager;
import fr.bebedlastreat.estacker.utils.Stacker;
import fr.bebedlastreat.estacker.utils.StackersManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
    private static Main instance;

    private List<Material> stackingBlocks;
    private List<Stacker> stackers;
    private int sneakingBreak;
    private int sneakingPlace;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        stackingBlocks = ConfigManager.getStackingBlocks();
        sneakingBreak = ConfigManager.getSneakingBreak();
        sneakingPlace = ConfigManager.getSneakingPlace();
        ConfigurationSerialization.registerClass(Stacker.class, "Stacker");
        stackers = StackersManager.loadStackers();
        for (Stacker stacker : stackers) {
            stacker.createHologram();
        }

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockListener(this), this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        for (Stacker stacker : stackers) {
            stacker.getHologram().destroy();
        }
        StackersManager.saveStackers(stackers);
        super.onDisable();
    }

    public static Main getInstance() {
        return instance;
    }

    public List<Material> getStackingBlocks() {
        return stackingBlocks;
    }

    public List<Stacker> getStackers() {
        return stackers;
    }

    public int getSneakingBreak() {
        return sneakingBreak;
    }

    public int getSneakingPlace() {
        return sneakingPlace;
    }
}
