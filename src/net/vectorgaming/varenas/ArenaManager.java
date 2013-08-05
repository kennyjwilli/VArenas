
package net.vectorgaming.varenas;

import com.google.common.io.Files;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vectorgaming.varenas.framework.ArenaLobby;
import net.vectorgaming.varenas.framework.ArenaSpectatorBox;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.ArenaSettings;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaManager 
{
    private static HashMap<String, Arena> arenas = new HashMap<>();
    private static HashMap<String, ArenaSettings> arenaSettings = new HashMap<>();
    private static HashMap<String, ArenaConfig> arenaConfigs = new HashMap<>();
    private static HashMap<String, Integer> arenaIdMap = new HashMap<>();
    private static HashMap<String, ArrayList<Arena>> runningArenasMap = new HashMap<>();
    
    private static ArrayList<String> runningArenas = new ArrayList<>();
    //private static ArrayList<String> readyArenas = new ArrayList<>();
    //private static ArrayList<Player> arenaPlayers = new ArrayList<>();
    private static VArenas plugin;
    
    private static final File FRAMEWORK_DIR = new File(plugin.getDataFolder().getAbsoluteFile()+File.separator+"framework");
    private static final File SETTINGS_DIR = new File(plugin.getDataFolder().getAbsoluteFile()+File.separator+"settings");
    private static final File SERVER_ROOT_DIR = new File("");
    private static final File MAPS_DIR = new File(SERVER_ROOT_DIR+File.separator+"maps");
    private static final File ARENAS_DIR = new File(SERVER_ROOT_DIR+File.separator+"arenas");
    
    public ArenaManager(VArenas instance)
    {
        plugin = instance;
    }
    
    public static void createMap(String map)
    {
        ArenaConfig framework = new ArenaConfig(plugin, new File(FRAMEWORK_DIR+File.separator+map.toLowerCase()+".yml"));
        ZoneConfig settings = new ZoneConfig(plugin, new File(SETTINGS_DIR+File.separator+map.toLowerCase()+".yml"));
        arenaConfigs.put(map.toLowerCase(), framework);
        arenaSettings.put(map.toLowerCase(), new ArenaSettings(map));
        
        //Set first arnea id 
    }
    
    public static ArenaConfig getArenaConfig(String map)
    {
        return arenaConfigs.get(map);
    }
    
    public static ArenaSettings getArenaSettings(String map)
    {
        return arenaSettings.get(map);
    }
    
    /**
     * Creates an arena from the specified map
     * @param mapName String 
     * @throws IOException
     */
    public static void createArena(String map) throws IOException
    {
        try
        {
            int arenaid = arenaIdMap.get(map);
            String arenaName = map.toLowerCase()+"_"+arenaid;
            //Copy the map to the arenas
            File mapFile = new File(MAPS_DIR+File.separator+map.toLowerCase());
            File arenaFile = new File(ARENAS_DIR+File.separator+arenaName);
            Files.copy(mapFile, arenaFile);
            //Delete uid.dat for new world
            File uid = new File(ARENAS_DIR+File.separator+map.toLowerCase()+"_"+arenaid+File.separator+"uid.dat");
            uid.delete();
            //Increment the arena id by one
            arenaIdMap.put(map, arenaid++);
            //Create new arena
            Arena arena = ArenaRegister.getNewMapInstance(arenaName, map);
            arena.start();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(ArenaManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    
    public ArrayList<Arena> getRunningArenasFromMap(String map)
    {
        return runningArenasMap.get(map);
    }
    
    public static boolean arenaExists(String arena)
    {
        if(runningArenas.contains(arena))
            return true;
        return false;
    }
    
    /**
     * Gets if the specified arena exists
     * NOTE: This will also return true for an arena that is in the process of being setup
     * @param arena VArena
     * @return Boolean
     */
    //public static boolean arenaExists(Arena arena) {return arenaExists(arena.getName());}
    
    /**
     * Gets all the arenas that have been fully setup
     * NOTE: These are the arenas that will actually be saved when the plugin shuts down
     * @return ArrayList<String>
     */
//    public static ArrayList<String> getReadyArenas() {return readyArenas;}
    
    /**
     * Gets if the specified arena is ready for use
     * @param name String
     * @return Boolean
     */
    public static boolean isArenaReady(String name)
    {
//        if(readyArenas.contains(name) && !getArena(name).isEditModeEnabled())
//            return true;
        return false;
    }
    
    /**
     * Gets if the specified arena is ready for use
     * @param arena VArena
     * @return Boolean
     */
    public static boolean isArenaReady(Arena arena) {return isArenaReady(arena.getName());}
    
    /**
     * Readys an arena
     * @param arena String
     */
    public static void readyArena(String arena)
    {
//        if(!readyArenas.contains(arena))
//            readyArenas.add(arena);
    }
    
    /**
     * Readys an arena
     * @param arena VArena
     */
    public static void readyArena(Arena arena) {readyArena(arena.getName());}
    
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
    
    /**
     * Gets the VAreans plugin
     * @return VArenas
     */
    public static VArenas getVArenasPlugin()
    {
        return plugin;
    }
    
}
