
package net.vectorgaming.varenas.framework.kits;

import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.VArenas;
import net.vectorgaming.varenas.framework.enums.ArenaDirectory;
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
    public static void removeKit(String name) {kits.remove(name);}
    
    /**
     * Checks to see if the given kit exists
     * @param name Name of the kit
     * @return boolean value
     */
    public static boolean kitExists(String name) {return kits.containsKey(name);}
        
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
        ZoneConfig config = new ZoneConfig(ArenaAPI.getPlugin(), new File(ArenaDirectory.KITS+File.separator+name+".yml"));
        Kit kit = new Kit(name);
        kit.setHelmet(getItemStackFromSave(config.getString("armor.helmet")));
        kit.setChestplate(getItemStackFromSave(config.getString("armor.chestplate")));
        kit.setLeggings(getItemStackFromSave(config.getString("armor.leggings")));
        kit.setBoots(getItemStackFromSave(config.getString("armor.boots")));
        for(String s : config.getStringList("inventory"))
            kit.addInventoryItem(getItemStackFromSave(s));
        kits.put(name, kit);
    }
    
    /**
     * Loads all the kits activated in the config.yml
     */
    public static void loadAllKits()
    {
        if(!plugin.getConfig().contains("kits"))
            return;
        for(String s : plugin.getConfig().getStringList("kits"))
            loadKit(s);
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
        
        if (item.getData().getData() == 0)
        {
            output += " ";
            output += item.getData().getData();
        }
        
        
        if(!item.getEnchantments().isEmpty())
        {
            output += " ";
            String enchantment = "";
            for(Enchantment e : item.getEnchantments().keySet())
            {
                enchantment += e.getName();
                enchantment += ":";
                enchantment += item.getEnchantmentLevel(e);
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
    public static ItemStack getItemStackFromSave(String saveString)
    {
        String[] split = saveString.split(" ");
        
        
        Material mat = Material.matchMaterial(split[0]);
        int amount = Integer.parseInt(split[1]);
        
        ItemStack result;
        
        if(split.length == 3)
        {
            byte data = Byte.parseByte(split[2]);
            result = new ItemStack(mat, amount, mat.getMaxDurability(), data);
        }else
        {
            result = new ItemStack(mat, amount);
        }
        
        String[] splitEnchant;
        
        if(split.length == 4)
        {
            splitEnchant = split[3].split(":");
            String enchantName = splitEnchant[0];
            int level = Integer.parseInt(splitEnchant[1]);
            result.addEnchantment(Enchantment.getByName(enchantName), level);
        }
        return result;
    }
}
