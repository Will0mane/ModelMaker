package me.will0mane.plugins.modelmaker.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.Objects;

public class Utils {

    public static ArmorStand createStand(Location location) {
        return Objects.requireNonNull(location.getWorld()).spawn(location, ArmorStand.class);
    }

}
