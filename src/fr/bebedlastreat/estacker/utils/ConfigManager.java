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
}
