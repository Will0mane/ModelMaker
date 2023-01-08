package me.will0mane.plugins.modelmaker;

import me.will0mane.plugins.modelmaker.commands.LoadModelCommand;
import me.will0mane.plugins.modelmaker.commands.LoadPetCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ModelMaker extends JavaPlugin {

    public static ModelMaker PLUGIN;

    @Override
    public void onEnable() {
        // Plugin startup logic
        PLUGIN = this;

        loadCommand("loadmodel", new LoadModelCommand());
        loadCommand("loadpet", new LoadPetCommand());
    }

    private void loadCommand(String commandName, CommandExecutor executor) {
        Objects.requireNonNull(getCommand(commandName)).setExecutor(executor);
    }

    @Override
    public void onDisable() {
    }

    public static ModelMaker getInstance(){
        return PLUGIN;
    }
}
