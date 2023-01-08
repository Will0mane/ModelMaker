package me.will0mane.plugins.modelmaker.commands;

import me.will0mane.plugins.modelmaker.ModelMaker;
import me.will0mane.plugins.modelmaker.model.FileLoadedModel;
import me.will0mane.plugins.modelmaker.pets.Pet;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class LoadPetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            if(sender.hasPermission("modelmaker.loadmodel")){
                String petPath = args[0];
                File file = new File(ModelMaker.getInstance().getDataFolder() + "/pets/" + petPath + ".pet");
                if(!file.exists()){
                    return false;
                }
                Player player =(Player) sender;
                Pet loadedPet = new Pet(Objects.requireNonNull(YamlConfiguration.loadConfiguration(file).getConfigurationSection("Settings")), player.getUniqueId());
                Location spawn = player.getLocation();
                loadedPet.spawn(spawn);
            }
        }
        return false;
    }
}
