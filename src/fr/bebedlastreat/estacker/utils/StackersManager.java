package fr.bebedlastreat.estacker.utils;

import fr.bebedlastreat.estacker.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StackersManager {

    private static File file = new File(Main.getInstance().getDataFolder(), "stackers.yml");
    private static YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public static int getNumberOfStacker() {
        int i = 0;
        while (configuration.get(String.valueOf(i)) != null) i++;
        return i;
    }

    public static void createStacker(Location location) {
        int id = getNumberOfStacker();
        configuration.set(id + ".location", location);
        configuration.set(id + ".value", 2);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exist(Location location) {
        for (int i = 0; i < getNumberOfStacker(); i++) {
            if (((Location) configuration.get(i + ".location")).equals(location)) return true;
        }
        return false;
    }

    public static int getStackByLocation(Location location) {
        for (int i = 0; i < getNumberOfStacker(); i++) {
            if (((Location) configuration.get(i + ".location")).equals(location)) return i;
        }
        return -1;
    }

    public static int getStackNumber(Location location) {
        return configuration.getInt(getStackByLocation(location) + ".value");
    }

    public static void setStackNumber(Location location, int number) {
        configuration.set(getStackByLocation(location) + ".value", number);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeStacker(Location location) {
        configuration.set(String.valueOf(getStackByLocation(location)), null);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
