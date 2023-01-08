package me.will0mane.plugins.modelmaker.model;

import me.will0mane.plugins.modelmaker.util.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ModelPiece {

    ModelPieceSettings settings;
    ArmorStand stand;
    boolean existsInWorld = false;

    public ModelPiece(ModelPieceSettings settings){
        this.settings =settings;
    }

    public ModelPieceSettings getSettings() {
        return settings;
    }

    public ArmorStand getStand() {
        return stand;
    }


    public void stopRendering(){
        if(stand != null && !stand.isDead()){
            stand.remove();
        }
        existsInWorld = false;
    }

    public void renderAt(Location location){
        if(existsInWorld){
            teleport(location);
            return;
        }
        stand = Utils.createStand(location);

        applySettings(settings);

        existsInWorld = true;
    }

    public void teleport(Location location) {
        renderAt(location);
    }

    public void applySettings(ModelPieceSettings settings) {
        if(stand == null) return;
        if(stand.isDead()) return;

        this.settings = settings;

        stand.setVisible(settings.isVisible);
        stand.setMarker(settings.hasMarker);
        stand.setGravity(settings.hasGravity);
        stand.setBasePlate(settings.basePlate);
        stand.setBasePlate(settings.basePlate);

        if(settings.head != null) Objects.requireNonNull(stand.getEquipment()).setHelmet(settings.head);
        if(settings.chest != null) Objects.requireNonNull(stand.getEquipment()).setChestplate(settings.chest);
        if(settings.leggings != null) Objects.requireNonNull(stand.getEquipment()).setLeggings(settings.leggings);
        if(settings.boots != null) Objects.requireNonNull(stand.getEquipment()).setBoots(settings.boots);

        if(settings.mainHand != null) Objects.requireNonNull(stand.getEquipment()).setItemInMainHand(settings.mainHand);
        if(settings.offHand != null) Objects.requireNonNull(stand.getEquipment()).setItemInOffHand(settings.offHand);
    }
}
