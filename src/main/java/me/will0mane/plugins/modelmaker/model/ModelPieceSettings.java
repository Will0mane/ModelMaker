package me.will0mane.plugins.modelmaker.model;

import lombok.Getter;
import me.will0mane.plugins.modelmaker.ModelItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ModelPieceSettings {

    @Getter
    boolean isSmall;
    @Getter
    boolean basePlate;
    @Getter
    boolean showArms;
    @Getter
    boolean isVisible;
    @Getter
    boolean hasMarker;
    @Getter
    boolean hasGravity;
    @Getter
    ItemStack head;
    @Getter
    ItemStack chest;
    @Getter
    ItemStack leggings;
    @Getter
    ItemStack boots;
    @Getter
    ItemStack mainHand;
    @Getter
    ItemStack offHand;
    @Getter
    double height, inFront, offsetX, offsetY, offsetZ;

    public ModelPieceSettings(boolean isSmall, boolean basePlate, boolean showArms, boolean isVisible, boolean hasMarker, boolean hasGravity,
                              ItemStack head, ItemStack chest, ItemStack leggings, ItemStack boots, ItemStack mainHand, ItemStack offHand,
                              double height, double inFront, double offsetX, double offsetY, double offsetZ){
        this.isSmall = isSmall;
        this.basePlate = basePlate;
        this.showArms = showArms;
        this.isVisible = isVisible;
        this.hasMarker = hasMarker;
        this.hasGravity = hasGravity;
        this.head = head;
        this.chest = chest;
        this.leggings = leggings;
        this.boots = boots;
        this.mainHand = mainHand;
        this.offHand = offHand;
        this.height = height;
        this.inFront = inFront;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public ModelPieceSettings(ConfigurationSection configuration){
        isSmall = configuration.contains("small") && configuration.getBoolean("small");
        basePlate = configuration.contains("basePlate") && configuration.getBoolean("basePlate");
        showArms = configuration.contains("showArms") && configuration.getBoolean("showArms");
        isVisible = configuration.contains("visible") && configuration.getBoolean("visible");
        hasMarker = configuration.contains("hittable") && !configuration.getBoolean("hittable");
        hasGravity = configuration.contains("gravity") && configuration.getBoolean("gravity");
        ConfigurationSection equipment = configuration.getConfigurationSection("equipment");
        if(equipment == null) return;
        head = ModelItem.getOrDefaultFromConfig(equipment.getConfigurationSection("head"), null);
        chest = ModelItem.getOrDefaultFromConfig(equipment.getConfigurationSection("chest"), null);
        leggings = ModelItem.getOrDefaultFromConfig(equipment.getConfigurationSection("legs"), null);
        boots = ModelItem.getOrDefaultFromConfig(equipment.getConfigurationSection("boots"), null);
        mainHand = ModelItem.getOrDefaultFromConfig(equipment.getConfigurationSection("mainHand"), null);
        offHand = ModelItem.getOrDefaultFromConfig(equipment.getConfigurationSection("offHand"), null);

        height = configuration.contains("height") ? configuration.getDouble("height") : 0;
        inFront = configuration.contains("inFront") ? configuration.getDouble("inFront") : 0;
        offsetX = configuration.contains("offsetX") ? configuration.getDouble("offsetX") : 0;
        offsetY = configuration.contains("offsetY") ? configuration.getDouble("offsetY") : 0;
        offsetZ = configuration.contains("offsetZ") ? configuration.getDouble("offsetZ") : 0;
    }



}
