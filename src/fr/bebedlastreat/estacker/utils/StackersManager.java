package fr.bebedlastreat.estacker.utils;

import fr.bebedlastreat.estacker.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackersManager {

    private static File file = new File(Main.getInstance().getDataFolder(), "stackers.yml");
    private static YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public static int getNumberOfStacker() {
        return Main.getInstance().getStackers().size();
    }

    public static List<Stacker> loadStackers() {
        List<Stacker> result = new ArrayList<>();
        for (String key : configuration.getConfigurationSection("").getKeys(false)) {
            result.add((Stacker) configuration.get(key, Stacker.class));
        }
        return result;
    }

    public static void createStacker(Stacker stacker) {
        Main.getInstance().getStackers().add(stacker);
    }

    public static void delete(Stacker stacker) {
        Main.getInstance().getStackers().remove(stacker);
    }

    public static void saveStackers(List<Stacker> stackers) {
        for (String key : configuration.getConfigurationSection("").getKeys(false)) {
            configuration.set(key, null);
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        for (Stacker zone : stackers) {
            configuration.set(String.valueOf(i), zone);
            i++;
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exist(Location location) {
        for (Stacker stacker : Main.getInstance().getStackers()) {
            if (stacker.getLoc().equals(location)) return true;
        }
        return false;
    }

    public static Stacker getStacker(Location location) {
        for (Stacker stacker : Main.getInstance().getStackers()) {
            if (stacker.getLoc().equals(location)) return stacker;
        }
        return null;
    }
}
