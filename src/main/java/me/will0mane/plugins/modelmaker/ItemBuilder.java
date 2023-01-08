package me.will0mane.plugins.modelmaker;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemBuilder {

    protected ItemStack itemStack;
    protected ItemMeta meta;

    public ItemBuilder(Material material){
        this.itemStack = new ItemStack(material);
        this.meta =itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name){
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setArmorColor(Color color){
        if(this.meta instanceof LeatherArmorMeta){
            ((LeatherArmorMeta) this.meta).setColor(color);
        }
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable){
        this.meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemStack build(){
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }

}
