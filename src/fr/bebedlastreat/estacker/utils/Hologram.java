package fr.bebedlastreat.estacker.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class Hologram {
    ArmorStand armorStand;

    public Hologram(Location loc, String name) {
        armorStand = loc.getWorld().spawn(loc, ArmorStand.class);
        armorStand.setCustomName(name);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setMarker(true);
    }

    public void updateText(String name) {
        armorStand.setCustomName(name);
    }

    public void destroy() {
        armorStand.remove();
        armorStand = null;
    }
}
