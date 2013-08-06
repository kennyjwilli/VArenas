
package net.vectorgaming.varenas;

import com.google.common.io.Files;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.ArenaDirectory;
import net.vectorgaming.varenas.framework.ArenaFramework;
import net.vectorgaming.varenas.framework.ArenaSettings;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class ArenaManager 
{
    private static HashMap<String, Arena> arenas = new HashMap<>(); //{ArenaName, ArenaObject}
    private static HashMap<String, ArenaSettings> arenaSettings = new HashMap<>(); // {MapName, ArenaSettings}
    private static HashMap<String, ArenaConfig> arenaConfigs = new HashMap<>(); // {MapName, ArenaConfig}
    private static HashMap<String, ArenaFramework> arenaFramework = new HashMap<>();
    private static HashMap<String, Integer> arenaIdMap = new HashMap<>(); // {MapName, nextIdForArena}
    private static HashMap<String, ArrayList<Arena>> runningArenasMap = new HashMap<>(); //{MapName, List of arenas}
    private static ArrayList<String> runningArenasList = new ArrayList<>();
    
    private static VArenas plugin = ArenaAPI.getPlugin();
    
    /**
     * Creates a map with the given name
     * @param map String
     */
    public static void createMap(String map)
    {
        ArenaConfig framework = new ArenaConfig(plugin, new File(ArenaDirectory.FRAMEWORK_DIR.toString()+File.separator+map.toLowerCase()+".yml"));
        ZoneConfig settings = new ZoneConfig(plugin, new File(ArenaDirectory.SETTINGS_DIR.toString()+File.separator+map.toLowerCase()+".yml"));
        arenaConfigs.put(map.toLowerCase(), framework);
        arenaSettings.put(map.toLowerCase(), new ArenaSettings(map));
        arenaFramework.put(map.toLowerCase(), new ArenaFramework());
        
        //Set first arnea id 
    }
    
    /**
     * Gets a list of all maps that have been created
     * @return ArrayList<String>
     */
    public static ArrayList<String> getMaps()
    {
        ArrayList<String> result = new ArrayList<>();
        for(String s : arenaSettings.keySet())
        {
            result.add(s);
        }
        return result;
    }
    
    /**
     * Gets the ArenaConfig for the specified map
     * @param map String
     * @return ArenaConfig
     */
    public static ArenaConfig getArenaConfig(String map)
    {
        return arenaConfigs.get(map.toLowerCase());
    }
    
    /**
     * Gets the arena framework for the specified arena
     * @param map Name of map
     * @return AreanFramework
     */
    public static ArenaFramework getAreanFramework(String map)
    {
        return arenaFramework.get(map.toLowerCase());
    }
    
    /**
     * Gets the settings for the map
     * @param map String
     * @return ArenaSettings
     */
    public static ArenaSettings getArenaSettings(String map)
    {
        return arenaSettings.get(map.toLowerCase());
    }
    
    /**
     * Creates an arena from the specified map.
     * This will start the arena
     * @param mapName String 
     */
    public static void createArena(String map)
    {
        //Setup some initial variables
        int arenaid = arenaIdMap.get(map);
        String arenaName = map.toLowerCase()+"_"+arenaid;
        
        //Copy the map to the arenas
        File mapFile = new File(ArenaDirectory.MAPS_DIR+File.separator+map.toLowerCase());
        File arenaFile = new File(ArenaDirectory.ARENAS_DIR+File.separator+arenaName);
        try
        {
            Files.copy(mapFile, arenaFile);
        } catch (IOException ex)
        {
            Logger.getLogger(ArenaManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Delete uid.dat for new world
        File uid = new File(ArenaDirectory.ARENAS_DIR+File.separator+map.toLowerCase()+"_"+arenaid+File.separator+"uid.dat");
        uid.delete();
        
        //Increment the arena id by one
        arenaIdMap.put(map, arenaid++);
        
        

        //Create new arena
        Arena arena = ArenaAPI.getArenaCreator(getArenaSettings(map).getType()).getNewArenaInstance();
        //Add arena to maps
        arenas.put(arenaName, arena);
        if(!runningArenasMap.get(map).contains(arena))
        {
            ArrayList<Arena> temp = runningArenasMap.get(map);
            temp.add(arena);
            runningArenasMap.put(map, temp);
        }
        if(!runningArenasList.contains(arena.getName()))
            runningArenasList.add(arena.getName());
        
        //Starts the arena
        arena.start();
    }
    
    /**
     * Generates the next avaliable id for the map
     * @param map String
     * @return Integer
     */
    public static Integer createArenaId(String map)
    {
        int arenaid = arenaIdMap.get(map);
        return arenaid++;
    }
    
    
    /**
     * Gets the arena from the specified name
     * @param name String
     * @return VArena
     */
    public static Arena getArena(String name){return arenas.get(name);}
    
    
    /**
     * Gets if the specified arena exists
     * NOTE: This will also return true for an arena that is in the process of being setup
     * @param name String
     * @return Boolean
     */
    public static boolean mapExists(String map)
    {
        if(arenaSettings.containsKey(map))
            return true;
        return false;
    }
    
    /**
     * Gets all the arenas that are running the specified map
     * @param map String
     * @return ArrayList<Arena>
     */
    public ArrayList<Arena> getRunningArenasFromMap(String map)
    {
        return runningArenasMap.get(map);
    }
    
    /**
     * Checks to see if the arena is running
     * @param arena Arena
     * @return boolean
     */
    public boolean isArenaRunning(Arena arena)
    {
        return isArenaRunning(arena.getName());
    }
    
    /**
     * Checks to see if the arena is running
     * @param name String
     * @return boolean
     */
    public boolean isArenaRunning(String name) 
    {
        if(runningArenasList.contains(name))
            return true;
        return false;
    }
    
    /**
     * Converts two locations into five locations for PolygonTriggerBox
     * @param minY Location
     * @param maxY Location
     * @return ArrayList<Location>
     */
    public static ArrayList<Location> getPolygonPoints(Location minY, Location maxY)
    {
        ArrayList<Location> result = new ArrayList<>();
        double xChange = maxY.getBlockX() - minY.getBlockX();
        double yChange = maxY.getBlockY() - minY.getBlockY();
        double zChange = maxY.getBlockZ() - minY.getBlockZ();

        Location loc1 = new Location(minY.getWorld(), minY.getBlockX() + xChange, minY.getBlockY(), minY.getBlockZ());
        Location loc2 = new Location(minY.getWorld(), minY.getBlockX(), minY.getBlockY() + yChange, minY.getBlockZ());
        Location loc3 = new Location(minY.getWorld(), minY.getBlockX(), minY.getBlockY(), minY.getBlockZ() + zChange);
        result.add(minY);
        result.add(maxY);
        result.add(loc1);
        result.add(loc2);
        result.add(loc3);
        return result;
    }
}
