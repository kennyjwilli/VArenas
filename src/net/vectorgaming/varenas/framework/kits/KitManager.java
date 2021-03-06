
package net.vectorgaming.varenas.framework.kits;

import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.Exceptions.KitFormatException;
import net.vectorgaming.varenas.VArenas;
import net.vectorgaming.varenas.framework.enums.ArenaDirectory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Kenny
 */
public class KitManager 
{
    private static VArenas plugin = ArenaAPI.getPlugin();
    
    private static HashMap<String,Kit> kits = new HashMap<>();
        
    /**
     * Gets a kit from the specified name
     * @param name Name of the kit
     * @return Kit object
     */
    public static Kit getKit(String name) {return kits.get(name);}
    
    /**
     * Adds a kit to the plugin
     * @param name Name of the kit
     * @param kit Kit object
     */
    public static void addKit(String name, Kit kit) {kits.put(name, kit);}
    
    /**
     * Removed a kit from the plugin
     * @param name Name of the kit
     */
    public static void removeKit(String name) {kits.remove(name.toLowerCase());}
    
    /**
     * Checks to see if the given kit exists
     * @param name Name of the kit
     * @return boolean value
     */
    public static boolean kitExists(String name) {return kits.containsKey(name.toLowerCase());}
        
    /**
     * Checks to see if the given kit exists
     * @param kit Kit object
     * @return boolean value
     */
    public static boolean kitExists(Kit kit) {return kitExists(kit.getName());}
    
    /**
     * Gets a list of all the kit names
     * @return A list of kit names
     */
    public static Set<String> getKitNames() {return kits.keySet();}
        
    /**
     * Saves the given kit name
     * 
     * NOTE: Does save a kit config file
     * @param name Name of the kit
     */
    public static void saveKit(String name)
    {
        Kit kit = getKit(name);
        ZoneConfig config = new ZoneConfig(ArenaAPI.getPlugin(), new File(ArenaDirectory.KITS+File.separator+name+".yml"));
        config.set("name", name);
        config.set("armor.helmet", itemStackToSaveString(kit.getHelmet()));
        config.set("armor.chestplate", itemStackToSaveString(kit.getChestplate()));
        config.set("armor.leggings", itemStackToSaveString(kit.getLeggings()));
        config.set("armor.boots", itemStackToSaveString(kit.getBoots()));
        
        List<String> inventory = new ArrayList<>();
        for(ItemStack item : kit.getInventoryContents())
            inventory.add(itemStackToSaveString(item));
        config.set("inventory", inventory);
        config.save();
        
        //Adds to enabled kits in config.yml
        List<String> kitsList;
        if(!plugin.getConfig().contains("kits"))
        {
            kitsList = new ArrayList<>();
        }else
        {
            kitsList = plugin.getConfig().getStringList("kits");
        }
        if(!kitsList.contains(name))
            kitsList.add(name);
        plugin.getConfig().set("kits", kitsList);
        plugin.saveConfig();
    }
    
    /**
     * Loads a kit through the given name
     * @param name Name of the kit
     */
    public static void loadKit(String name)
    {
        try
        {
            ZoneConfig config = new ZoneConfig(ArenaAPI.getPlugin(), new File(ArenaDirectory.KITS+File.separator+name+".yml"));
            Kit kit = new Kit(name);
            kit.setHelmet(getItemStackFromSave(config.getString("armor.helmet")));
            kit.setChestplate(getItemStackFromSave(config.getString("armor.chestplate")));
            kit.setLeggings(getItemStackFromSave(config.getString("armor.leggings")));
            kit.setBoots(getItemStackFromSave(config.getString("armor.boots")));
            for(String s : config.getStringList("inventory"))
                kit.addInventoryItem(getItemStackFromSave(s));
            kits.put(name, kit);
        } catch (KitFormatException ex)
        {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Loads all the kits activated in the config.yml
     */
    public static void loadAllKits()
    {
        kits.clear();
        plugin.reloadConfig();
        if(!plugin.getConfig().contains("kits"))
            return;
        for(String s : plugin.getConfig().getStringList("kits"))
            loadKit(s);
    }
    
    /**
     * Checks to see if the kit is saved to the config
     * @param name Name of the kit
     * @return boolean value
     */
    public static boolean isSavedToConfig(String name)
    {
        if(!plugin.getConfig().contains("kits"))
            return false;
        return plugin.getConfig().getStringList("kits").contains(name);
    }
    
    /**
     * Saves all the kits and enabled them in the config.yml 
     */
    public static void saveAllKits()
    {
        for(String s : kits.keySet())
            saveKit(s);
    }
    
    /**
     * Saves an ItemStack to a String in a format that can be loaded
     * @param item ItemStack object
     * @return Save string for the ItemStack
     */
    public static String itemStackToSaveString(ItemStack item)
    {
        String output = "";
        output += item.getType().name();
        output += " ";
        output += item.getAmount();
        
        if (item.getData().getData() != 0)
        {
            output += " ";
            output += item.getData().getData();
        }
        
        
        if(!item.getEnchantments().isEmpty())
        {
            output += " ";
            String enchantment = "";
            int i = 1;
            for(Enchantment e : item.getEnchantments().keySet())
            {
                enchantment += e.getName();
                enchantment += ":";
                enchantment += item.getEnchantmentLevel(e);
                if(i != item.getEnchantments().keySet().size())
                    enchantment += ",";
                i++;
            }
            output += enchantment;
        }
        
        return output;
    }
    
    /**
     * Gets an ItemStack from its save string
     * @param saveString ItemStack save string
     * @return ItemStack object
     */
    public static ItemStack getItemStackFromSave(String saveString) throws KitFormatException
    {
        String[] split = saveString.split(" ");
        
        Material mat = Material.matchMaterial(split[0]);
        int amount = Integer.parseInt(split[1]);
        byte data;
        
        ItemStack result;
        
        try
        {
            result = new ItemStack(mat, amount);
        }catch(Exception e)
        {
            Bukkit.getLogger().log(Level.WARNING, "Could not load ItemStack from save string: "+saveString);
            throw new KitFormatException();
        }
        
        if(split.length == 3)
        {
            try
            {
                data = Byte.parseByte(split[2]);
                result = new ItemStack(mat, amount, mat.getMaxDurability(), data);
            }catch(Exception e){}
            
            try
            {
                result = addEnchantmentFromSave(result, split[2]);
            }catch(Exception e){}
            return result;
        }
        if(split.length == 4)
        {
            try
            {
                data = Byte.parseByte(split[2]);
                result = new ItemStack(mat, amount, mat.getMaxDurability(), data);
                result = addEnchantmentFromSave(result, split[3]);
            }catch(Exception e) {}
        }
        return result;
    }
    
    private static ItemStack addEnchantmentFromSave(ItemStack item, String enchantSave)
    {
        ItemStack result = item;
        String[] splitEnchant = enchantSave.split(",");
        int i = 0;
        for(String s : splitEnchant)
        {
            String[] splitFinal = splitEnchant[i].split(":");
            String enchantName = splitFinal[0];
            int level = Integer.parseInt(splitFinal[1]);
            result.addEnchantment(Enchantment.getByName(enchantName), level);
            i++;
        }
        return result;
    }
}
