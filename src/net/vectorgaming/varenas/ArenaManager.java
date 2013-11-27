
package net.vectorgaming.varenas;

import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneTools;
import info.jeppes.ZoneWorld.WorldLoader;
import info.jeppes.ZoneWorld.ZoneWorld;
import info.jeppes.ZoneWorld.ZoneWorldAPI;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.arcanerealm.arenasigns.ArenaSignsAPI;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.enums.ArenaDirectory;
import net.vectorgaming.varenas.framework.ArenaFramework;
import net.vectorgaming.varenas.framework.ArenaLobby;
import net.vectorgaming.varenas.framework.ArenaSettings;
import net.vectorgaming.varenas.framework.ArenaSpectatorBox;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import org.apache.commons.io.FileUtils;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class ArenaManager 
{
    private static final HashMap<String, Arena> arenas = new HashMap<>(); //{ArenaName, ArenaObject}
    private static HashMap<String, ArenaSettings> arenaSettings = new HashMap<>(); // {MapName, ArenaSettings}
    private static HashMap<String, ArenaConfig> arenaConfigs = new HashMap<>(); // {MapName, ArenaConfig}
    private static HashMap<String, ArenaFramework> arenaFramework = new HashMap<>();
    private static final HashMap<String, Integer> arenaIdMap = new HashMap<>(); // {MapName, nextIdForArena}
    private static final ArrayList<String> queuedArenas = new ArrayList<>();
    
    private static final VArenas plugin = ArenaAPI.getPlugin();
    
    /**
     * Creates a map with the given name
     * @param map String
     */
    public static void createMap(String map)
    {
        File f = new File(ArenaDirectory.ARENA_FRAMEWORK);
        File f1 = new File(ArenaDirectory.ARENA_SETTINGS);
        f.mkdirs();
        f1.mkdirs();
        ArenaConfig framework = new ArenaConfig(plugin, new File(ArenaDirectory.ARENA_FRAMEWORK+File.separator+map.toLowerCase()+".yml"));
        ZoneConfig settings = new ZoneConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS+File.separator+map.toLowerCase()+".yml"));
        arenaConfigs.put(map.toLowerCase(), framework);
        arenaSettings.put(map.toLowerCase(), new ArenaSettings(map));
        arenaFramework.put(map.toLowerCase(), new ArenaFramework(map));
        //Set first arnea id 
    }
    
    /**
     * Sets all settings, config, and framework HashMaps to a new map to clear all data. This method should
     * generally be used before the SLAPI.loadArena method.
     */
    public static void reloadHashMaps()
    {
        arenaSettings = new HashMap<>();
        arenaConfigs = new HashMap<>();
        arenaFramework = new HashMap<>();
    }
    
    /**
     * Checks to see if the map has been saved in the config yet or if it is just saved in the RAM
     * @param map Name of the map
     * @return boolean value
     */
    public static boolean isMapSavedToConfig(String map)
    {
        List<String> list = plugin.getConfig().getStringList("enabled-arenas");
        return !list.isEmpty() && list.contains(map);
    }
    
    /**
     * Gets a list of all maps that have been created
     * @return ArrayList<String>
     */
    public static ArrayList<String> getMaps()
    {
        ArrayList<String> result = new ArrayList<>();
        for(String s : arenaSettings.keySet())
            result.add(s);
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
     * Gets the arena framework for the specified arena
     * @param arena Name of the map
     * @return ArenaFramework object
     */
    public static ArenaFramework getArenaFramework(Arena arena)
    {
        return arenaFramework.get(arena.getMap().toLowerCase());
    }
    
    /**
     * Gets the settings for the map
     * @param map Name of the map
     * @return ArenaSettings
     */
    public static ArenaSettings getArenaSettings(String map)
    {
        return arenaSettings.get(map.toLowerCase());
    }
    
    public static ArenaSettings getArenaSettings(Arena arena)
    {
        if(arena.getMap() == null)
            System.out.println("-------------------- Null map");
        return arenaSettings.get(arena.getMap().toLowerCase());
    }
    
    /**
     * Adds an arena to the queue list
     * 
     * This is used for creating arenas by command
     * @param arena Arena object
     */
    public static void queueArena(Arena arena)
    {
        queuedArenas.add(arena.getName());
    }
    
    /**
     * Gets a list of all the queued arenas
     * @return A list of arenas
     */
    public static ArrayList<String> getQueuedArenas() {return queuedArenas;}
    
    /**
     * Gets if the specified arena is queued
     * @param arena Arena object
     * @return boolean value
     */
    public static boolean isArenaQueued(Arena arena)
    {
        return isArenaQueued(arena.getName());
    }
    
    /**
     * Gets if the specified arena is queued
     * @param arena Name of the arena
     * @return boolean value
     */
    public static boolean isArenaQueued(String arena)
    {
        return queuedArenas.contains(arena);
    }
    
    /**
     * Gets if the arena is able to start or not
     * @param arena Arena object
     * @return boolean value
     */
    public static boolean canArenaStart(Arena arena)
    {
        return ArenaPlayerManager.getPlayersInArena(arena).size() >= ArenaManager.getArenaSettings(arena).getMinPlayers();
    }
    
    /**
     * Creates an arena from the specified map.
     * @param map Name of the map to start an arena off of
     * @param start If the match should start or not
     * @return Arena created
     */
    public static Arena createArena(String map, boolean start)
    {
        return createArena(map, map+"_"+getNextArenaId(map), start);
    }
    
    /**
     * Creates an arena with the given map and the given name. The name of the arena
     * should be the map name followed by an underscore and the arena Id. 
     * 
     * If you need to generate an arena without a specific name then use the createArena(String map, boolean start)
     * method.
     * @param map Name of the map the arena is based off
     * @param name Name given to the arena
     * @param start If the arena should start once it has been created
     * @return The created Arena
     */
    public static Arena createArena(String map, String name, boolean start)
    {
        //Creates the world for the arena
        ZoneWorld zWorld = createMapWorld(map, name);

        //Create new arena
        Arena arena = ArenaAPI.getArenaCreator(getArenaSettings(map).getType()).getNewArenaInstance(name, map, zWorld);
        
        //Add arena to maps
        arenas.put(name, arena);
        
        //Setup the lobby and spectator spawns
        arena.setLobby(new ArenaLobby(name, zWorld));
        arena.setSpectatorBox(new ArenaSpectatorBox(name, zWorld));
        
        //Updates the ArenaSign
        ArenaSignsAPI.updateAllArenaSigns(name);
        
        //Starts the arena if required to
        if(start) 
        {
            arena.start();
        }
        
        return arena;
    }
    
    public static void deleteArena(Arena arena)
    {
        deleteArena(arena.getName());
    }
    
    public static void deleteArena(String arena)
    {
        arenas.remove(arena);
        if(queuedArenas.contains(arena))
        {
            queuedArenas.remove(arena);
        }
    }
    
    /**
     * Creates a world based off the given map name. This map must exist in the maps
     * directory. This also sets all world settings to default arena world settings.
     * @param map Name of the map
     * @param worldName The name of the world that will be created
     * @return The created world
     */
    public static ZoneWorld createMapWorld(String map, String worldName)
    {
        //Copy the map to the arenas
        File mapFile = new File(ArenaDirectory.MAPS+File.separator+map.toLowerCase());
        File arenaFile = new File(ArenaDirectory.ARENAS+File.separator+worldName);
        ZoneTools.deleteDirectory(arenaFile);
        try {
            FileUtils.copyDirectory(mapFile, arenaFile, new FileFilter(){
                @Override
                public boolean accept(File pathname) {
                    return !pathname.getName().equals("uid.dat") && !pathname.getName().equalsIgnoreCase("session.lock");
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(VArenas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Loads the world into ZoneWorld
        WorldLoader worldLoader = new WorldLoader(ZoneWorldAPI.getPlugin(), "arenas/"+worldName);
        worldLoader.setGenerator("empty");
        ZoneWorld zWorld = worldLoader.loadZoneWorld();
        zWorld.setPVP(false);
        return zWorld;
    }
    
    /**
     * Gets a list of all the worlds that arenas have ran in.
     * 
     * NOTE: This list is primarily kept to unload and deleted worlds when the plugin gets disabled.
     * It contains ALL worlds that arenas have ran in, are running in, or will run in.
     * @return A list of ZoneWorlds
     */
    public static ArrayList<ZoneWorld> getArenaWorlds() 
    {
        ArrayList<ZoneWorld> result = new ArrayList<>();
        for(Arena arena : getArenas())
        {
            result.add(arena.getWorld());
        }
        return result;
    }
    
    /**
     * Gets the next arena id for the given map
     * @param map Name of the map
     * @return The generated Id
     */
    public static Integer getNextArenaId(String map)
    {
        if(!arenaIdMap.containsKey(map))
        {
            arenaIdMap.put(map, 1);
            return 1;
        }else
        {
            return arenaIdMap.get(map) + 1;
        }
    }
    
    
    /**
     * Gets the arena from the specified name
     * @param name String
     * @return VArena
     */
    public static Arena getArena(String name)
    {
        return arenas.get(name);
    }
    
    public static ArrayList<Arena> getArenas()
    {
        ArrayList<Arena> result = new ArrayList<>();
        for(Arena a : arenas.values())
        {
            result.add(a);
        }
        return result;
    }
    
    
    /**
     * Gets if the specified arena exists
     * NOTE: This will also return true for an arena that is in the process of being setup
     * @param map
     * @return Boolean
     */
    public static boolean mapExists(String map)
    {
        return arenaSettings.containsKey(map);
    }
    
    /**
     * Checks to see if the arena is running
     * @param arena Arena
     * @return boolean
     */
    public static boolean isArenaRunning(Arena arena)
    {
        return isArenaRunning(arena.getName());
    }
    
    /**
     * Checks to see if the arena is running
     * @param name String
     * @return boolean
     */
    public static boolean isArenaRunning(String name) 
    {
        return arenas.containsKey(name);
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
