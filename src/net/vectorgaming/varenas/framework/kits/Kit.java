
package net.vectorgaming.varenas.framework.kits;

import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Kenny
 */
public class Kit
{
    private String name;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    
    private ArrayList<ItemStack> inventory = new ArrayList<>();
    
    public Kit(String name)
    {
        this.name = name;
    }
    
    public Kit(String name, ItemStack[] armor, ArrayList<ItemStack> inventory)
    {
        setArmorContents(armor);
        this.inventory = inventory;
    }
    
    public String getName() {return name;}
    
    public ItemStack[] getArmorContents(){return new ItemStack[]{boots, leggings, chestplate, helmet};}
    
    public final void setArmorContents(ItemStack[] items) 
    {
        helmet = items[3];
        chestplate = items[2];
        leggings = items[1];
        boots = items[0];
    }
    
    public ItemStack getHelmet() {return helmet;}
    
    public void setHelmet(ItemStack item) {helmet = item;}
    
    public ItemStack getChestplate() {return chestplate;}
    
    public void setChestplate(ItemStack item) {chestplate = item;}
    
    public ItemStack getLeggings() {return leggings;}
    
    public void setLeggings(ItemStack item) {leggings = item;}
    
    public ItemStack getBoots() {return boots;}
    
    public void setBoots(ItemStack item) {boots = item;}
    
    public ArrayList<ItemStack> getInventoryContents() {return inventory;}
    
    public void setInventoryContents(ItemStack[] items) 
    {      
        ArrayList<ItemStack> result = new ArrayList<>();
        for(ItemStack item : items)
        {
            if(item != null)
                result.add(item);
        }
        inventory = result;
    }
    
    public void addInventoryItem(ItemStack... item) {inventory.addAll(Arrays.asList(item));}
    
    public void giveKit(Player p, boolean clearInventory)
    {
        if(clearInventory)
            p.getInventory().clear();
        p.getInventory().setArmorContents(getArmorContents());
        for(ItemStack item : inventory)
            p.getInventory().addItem(item);
    }
}
