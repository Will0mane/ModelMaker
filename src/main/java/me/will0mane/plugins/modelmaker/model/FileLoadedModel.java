package me.will0mane.plugins.modelmaker.model;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FileLoadedModel implements Model {

    File source;
    YamlConfiguration configuration;
    Map<ModelPiece, ModelPieceSettings> settingsMap = new HashMap<>();
    Location curCenterLoc;

    public FileLoadedModel(File file){
        assert file != null;
        assert file.getName().endsWith("model");
        this.source = file;
        configuration = YamlConfiguration.loadConfiguration(source);
        loadDefaultSettings();
    }

    public void loadDefaultSettings() {
        String piecesPath = "Pieces";
        if(configuration.contains(piecesPath)){
            Objects.requireNonNull(configuration.getConfigurationSection(piecesPath)).getKeys(false).forEach(piece -> {
                String pieceName = configuration.getString(piecesPath + "." + piece + ".pieceName");
                ModelPieceSettings settings = new ModelPieceSettings(Objects.requireNonNull(configuration.getConfigurationSection(piecesPath + "." + piece + ".settings")));
                settingsMap.put(new ModelPiece(settings), settings);
            });
        }
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public File getSource() {
        return source;
    }

    @Override
    public Map<ModelPiece, ModelPieceSettings> piecesWithSettings() {
        return settingsMap;
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public void applySettings(ModelPiece modelPiece, ModelPieceSettings settings) {
        settingsMap.put(modelPiece, settings);
        modelPiece.applySettings(settings);
    }

    @Override
    public void teleportTo(Location location) {
        curCenterLoc = location;
        calculateAllPiecesLocations().forEach(ModelPiece::teleport);
    }

    @Override
    public Map<ModelPiece, Location> calculateAllPiecesLocations() {
        Map<ModelPiece, Location> returns = new HashMap<>();
        piecesWithSettings().forEach((modelPiece, settings) -> {
            Location center = curCenterLoc.clone();
            Location inFront = center.clone().add(center.getDirection().multiply(settings.inFront)).add(0,settings.height,0);
            returns.put(modelPiece, inFront.add(settings.offsetX, settings.offsetY, settings.offsetZ));
        });
        return returns;
    }
}
