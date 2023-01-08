package me.will0mane.plugins.modelmaker.commands;

import me.will0mane.plugins.modelmaker.ModelMaker;
import me.will0mane.plugins.modelmaker.model.FileLoadedModel;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class LoadModelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            if(sender.hasPermission("modelmaker.loadmodel")){
                String modelPath = args[0];
                File file = new File(ModelMaker.getInstance().getDataFolder() + "/models/" + modelPath + ".model");
                if(!file.exists()){
                    return false;
                }
                FileLoadedModel loadedModel = new FileLoadedModel(file);
                Player player =(Player) sender;
                Location spawn = player.getLocation();
                loadedModel.teleportTo(spawn);
            }
        }
        return false;
    }
}
