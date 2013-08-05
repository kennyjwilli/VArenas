package net.vectorgaming.varenas;

import java.io.File;
import java.util.HashMap;

/**
 *
 * @author jeppe
 */
public class ArenaAPI 
{
    private static VArenas plugin;
    private static HashMap<String, ArenaCreator> maps = new HashMap<>();
    
    public ArenaAPI(VArenas plugin)
    {
        ArenaAPI.plugin = plugin;
    }

    /**
     * Gets the VArenas plugin
     * @return VArenas
     */
    public static VArenas getPlugin() 
    {
        return plugin;
    }
    
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
}
