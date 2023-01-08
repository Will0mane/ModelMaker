package me.will0mane.plugins.modelmaker;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ModelItem {

    public static ItemStack getOrDefaultFromConfig(ConfigurationSection configurationSection, ItemStack defaultItem) {
        if(configurationSection == null) return defaultItem;
        return new ItemBuilder(
                configurationSection.contains("material") ?
                        Material.valueOf(configurationSection.getString("material")) : Material.STONE).setDisplayName(configurationSection.contains("displayName") ?
                configurationSection.getString("displayName") : "Default Name").build();
    }

}
