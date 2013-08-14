package net.vectorgaming.varenas;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 *
 * @author jeppe
 */
public class ArenaAPI 
{
    private static VArenas plugin;
    private static World hubWorld;
    private static HashMap<String, ArenaCreator> maps = new HashMap<>();
    
    public ArenaAPI(VArenas plugin)
    {
        ArenaAPI.plugin = plugin;
        hubWorld = Bukkit.getWorld(plugin.getConfig().getString("hub-world"));
        if(hubWorld == null)
        {
            Bukkit.getLogger().log(Level.SEVERE, "[VArenas] Hub world \""+plugin.getConfig().getString("hub-world")+"\" does not exist!");
            Bukkit.getLogger().log(Level.SEVERE, "[VArenas] Disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    /**
     * Gets the VArenas plugin
     * @return VArenas
     */
    public static VArenas getPlugin() 
    {
        return plugin;
    }
    
    /**
     * Gets the plugin data folder
     * @return File object
     */
    public static File getDataFolder() 
    {
        return plugin.getDataFolder();
    }
    
    /**
     * Registers an ArenaCreator for a map type
     * @param type String
     * @param arenaCreator ArenaCreator
     */
    public static void registerArenaCreator(String type, ArenaCreator arenaCreator)
    {
        maps.put(type, arenaCreator);
    }
    
    /**
     * Unregisters an ArenaCreator for a map type
     * @param type String
     */
    public static void unregisterArenaCreator(String type)
    {
        maps.remove(type);
    }
    
    /**
     * Gets the ArenaCreator for the specified type
     * @param type String
     * @return ArenaCreator
     */
    public static ArenaCreator getArenaCreator(String type)
    {
        return maps.get(type);
    }
    
    /**
     * Gets the hub world
     * @return Bukkit world
     */
    public static World getHubWorld()
    {
        return hubWorld;
    }

}
