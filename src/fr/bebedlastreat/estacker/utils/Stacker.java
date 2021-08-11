package fr.bebedlastreat.estacker.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Stacker implements ConfigurationSerializable {
    private Location loc;
    private int value;
    private Material material;
    private Hologram hologram;

    public Stacker(Location loc, int value, Material material) {
        this.loc = loc;
        this.value = value;
        this.material = material;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap();
        data.put("loc", this.loc);
        data.put("value", this.value);
        data.put("material", this.material.toString());
        return data;
    }

    public static Stacker deserialize(Map<String, Object> args) {
        Stacker stacker = new Stacker((Location) args.get("loc"), NumberConversions.toInt(args.get("value")), Material.valueOf(String.valueOf(args.get("material"))));
        return stacker;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getMaterialText() {
        return material.toString().toUpperCase().substring(0, 1) + material.toString().toLowerCase().substring(1).replace("_", " ");
    }

    public void createHologram() {
        this.hologram = new Hologram(loc.clone().add(0.5, 1, 0.5), "§7" + getMaterialText() + " §6x" + value);
    }

    public void updateHologram() {
        this.hologram.updateText("§7" + getMaterialText() + " §6x" + value);
    }

    public void deleteHologram() {
        this.hologram.destroy();
        this.hologram = null;
    }
}
