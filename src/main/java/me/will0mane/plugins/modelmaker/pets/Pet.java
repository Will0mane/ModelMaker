package me.will0mane.plugins.modelmaker.pets;

import me.will0mane.plugins.modelmaker.ModelMaker;
import me.will0mane.plugins.modelmaker.model.FileLoadedModel;
import me.will0mane.plugins.modelmaker.model.Model;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class Pet implements Listener {

    protected UUID owner;
    protected Model representingModel;
    protected Entity currentTarget;
    protected Entity movingDummy;
    protected Location lastSpawnLoc;
    protected EntityType boxType;
    protected int taskMove = -1;

    public Pet(ConfigurationSection section, UUID owner){
        this.owner = owner;
        String modelPath = section.contains("modelPath") ? section.getString("modelPath") : "null";
        this.representingModel = new FileLoadedModel(new File(ModelMaker.getInstance().getDataFolder() + "/models/" + modelPath + ".model"));
        this.currentTarget = Bukkit.getPlayer(owner);
        this.boxType = EntityType.valueOf(section.contains("boxType") ? section.getString("boxType") : "SHEEP");
        Bukkit.getPluginManager().registerEvents(this, ModelMaker.getInstance());
    }

    public Pet(UUID owner, Model model, EntityType box){
        this.owner = owner;
        this.representingModel = model;
        this.currentTarget = Bukkit.getPlayer(owner);
        this.boxType = box;
        Bukkit.getPluginManager().registerEvents(this, ModelMaker.getInstance());
    }


    public void spawn(Location location){
        if(movingDummy != null) movingDummy.remove();
        lastSpawnLoc = location;
        movingDummy = Objects.requireNonNull(location.getWorld()).spawnEntity(location, boxType);
        if(!(movingDummy instanceof Mob)){
            throw new UnsupportedOperationException("Could not use this hitbox type! Please use a mob instead!");
        }
        updateFollowGoal();
        representingModel.teleportTo(location);
        if(taskMove != -1){
            Bukkit.getScheduler().cancelTask(taskMove);
        }
        taskMove = new BukkitRunnable(){

            @Override
            public void run() {
                if(movingDummy == null || movingDummy.isDead()) {
                    cancel();
                    return;
                }
                representingModel.teleportTo(movingDummy.getLocation());
                ((Mob) movingDummy).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5, 1));
            }
        }.runTaskTimer(ModelMaker.getInstance(), 0,1).getTaskId();
    }

    private void updateFollowGoal() {
        if(movingDummy == null || movingDummy.isDead() || currentTarget == null || currentTarget.isDead() || !(currentTarget instanceof LivingEntity)) return;
        ((Mob) movingDummy).setTarget((LivingEntity) currentTarget);
    }

    public void setCurrentTarget(Entity currentTarget) {
        this.currentTarget = currentTarget;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(movingDummy == null || movingDummy.isDead()) return;
        if(e.getEntity().getUniqueId() == movingDummy.getUniqueId()){
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e){
        if(movingDummy == null || movingDummy.isDead()) return;
        if(e.getEntity().getUniqueId() == movingDummy.getUniqueId()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onOwnerQuit(PlayerQuitEvent e){
        if(e.getPlayer().getUniqueId() == owner){
            currentTarget = null;
            representingModel.removeFromWorld();
        }
    }

    @EventHandler
    public void onOwnerJoin(PlayerJoinEvent e){
        if(e.getPlayer().getUniqueId() == owner){
            currentTarget = e.getPlayer();
            updateFollowGoal();
            lastSpawnLoc = e.getPlayer().getLocation();
            representingModel.teleportTo(lastSpawnLoc);
        }
    }

}
