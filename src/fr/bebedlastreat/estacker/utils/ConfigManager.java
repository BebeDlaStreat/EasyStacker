package fr.bebedlastreat.estacker.utils;

import fr.bebedlastreat.estacker.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    public static List<Material> getStackingBlocks() {
        List<?> list = Main.getInstance().getConfig().getList("stacking-blocks");
        List<Material> result = new ArrayList<>();
        for (Object o : list) {
            try {
                Material type = Material.valueOf(o.toString());
                if (type.isBlock()) {
                    result.add(type);
                } else {
                    Bukkit.getConsoleSender().sendMessage("§c[EasyStacker] unable to load config: " + type.toString() + " is not a block");
                }
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("§c[EasyStacker] unable to load config: " + o.toString() + " is not a valid material");
            }
        }
        return result;
    }

    public static int getSneakingBreak() {
        String s = Main.getInstance().getConfig().getString("sneaking-break");
        if (s.equalsIgnoreCase("ALL")) {
            return -1;
        }
        if (s.equalsIgnoreCase("ANY")) {
            return 0;
        }
        if (isAnInteger(s)) {
            int i = Integer.parseInt(s);
            if (i > 0) {
                return i;
            } else {
                Bukkit.getConsoleSender().sendMessage("§c[EasyStacker] unable to load config: number value for \"sneaking-break\" need to be positive");
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("§c[EasyStacker] unable to load config: " + s + " is not a valid data for \"sneaking-break\"");
        }
        return 0;
    }

    public static int getSneakingPlace() {
        String s = Main.getInstance().getConfig().getString("sneaking-place");
        if (s.equalsIgnoreCase("HAND")) {
            return 1;
        }
        if (s.equalsIgnoreCase("ANY")) {
            return 0;
        }
        Bukkit.getConsoleSender().sendMessage("§c[EasyStacker] unable to load config: " + s + " is not a valid data for \"sneaking-place\"");
        return 0;
    }

    public static boolean isAnInteger(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }
}
