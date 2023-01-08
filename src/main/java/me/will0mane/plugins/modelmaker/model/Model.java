package me.will0mane.plugins.modelmaker.model;

import org.bukkit.Location;

import java.util.Map;

public interface Model {

    public Map<ModelPiece, ModelPieceSettings> piecesWithSettings();
    public String id();
    public void applySettings(ModelPiece modelPiece, ModelPieceSettings settings);
    public void teleportTo(Location location);
    public Map<ModelPiece, Location> calculateAllPiecesLocations();

}
