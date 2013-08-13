
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static HashMap<String, Arena> arenas = new HashMap<>(); //{ArenaName, ArenaObject}
    private static HashMap<String, ArenaSettings> arenaSettings = new HashMap<>(); // {MapName, ArenaSettings}
    private static HashMap<String, ArenaConfig> arenaConfigs = new HashMap<>(); // {MapName, ArenaConfig}
    private static HashMap<String, ArenaFramework> arenaFramework = new HashMap<>();
    private static HashMap<String, Integer> arenaIdMap = new HashMap<>(); // {MapName, nextIdForArena}
    private static HashMap<String, ArrayList<Arena>> runningArenasMap = new HashMap<>(); //{MapName, List of arenas}
    private static ArrayList<String> queuedArenas = new ArrayList<>();
    private static ArrayList<String> runningArenasList = new ArrayList<>();
    private static ArrayList<ZoneWorld> arenaWorlds = new ArrayList<>();
    
    private static VArenas plugin = ArenaAPI.getPlugin();
    
    /**
     * Creates a map with the given name
     * @param map String
     */
    public static void createMap(String map)
    {
        File f = new File(ArenaDirectory.ARENA_FRAMEWORK_DIR);
        File f1 = new File(ArenaDirectory.ARENA_SETTINGS_DIR);
        f.mkdirs();
        f1.mkdirs();
        ArenaConfig framework = new ArenaConfig(plugin, new File(ArenaDirectory.ARENA_FRAMEWORK_DIR+File.separator+map.toLowerCase()+".yml"));
        ZoneConfig settings = new ZoneConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS_DIR+File.separator+map.toLowerCase()+".yml"));
        arenaConfigs.put(map.toLowerCase(), framework);
        arenaSettings.put(map.toLowerCase(), new ArenaSettings(map));
        arenaFramework.put(map.toLowerCase(), new ArenaFramework(map));
        
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
        if(queuedArenas.contains(arena))
            return true;
        return false;
    }
    
    /**
     * Gets if the arena is able to start or not
     * @param arena Arena object
     * @return boolean value
     */
    public static boolean canArenaStart(Arena arena)
    {
        if(ArenaPlayerManager.getPlayersInArena(arena).size() >= ArenaManager.getArenaSettings(arena).getMinPlayers())
            return true;
        return false;
    }
    
    /**
     * Creates an arena from the specified map.
     * This will start the arena
     * @param mapName Name of map 
     */
    public static Arena createArena(String map, boolean start)
    {
        if(!arenaIdMap.containsKey(map))
            arenaIdMap.put(map, 1);
        //Setup some initial variables
        int arenaid = arenaIdMap.get(map);
        String arenaName = map.toLowerCase()+"_"+arenaid;
        
        //Copy the map to the arenas
        File mapFile = new File(ArenaDirectory.MAPS_DIR+File.separator+map.toLowerCase());
        File arenaFile = new File(ArenaDirectory.ARENAS_DIR+File.separator+arenaName);
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
        WorldLoader worldLoader = new WorldLoader(ZoneWorldAPI.getPlugin(), "arenas/"+arenaName);
        ZoneWorld zWorld = worldLoader.loadZoneWorld();
        
        //Increment the arena id by one
        arenaid++;
        arenaIdMap.put(map, arenaid);

        //Create new arena
        Arena arena = ArenaAPI.getArenaCreator(getArenaSettings(map).getType()).getNewArenaInstance(arenaName, map, zWorld);
        
        //Add arena to maps
        arenas.put(arenaName, arena);
        arenaWorlds.add(zWorld);
        if(!runningArenasMap.containsKey(map))
        {
            runningArenasMap.put(map, new ArrayList<Arena>());
        }
        if(!runningArenasMap.get(map).contains(arena))
        {
            ArrayList<Arena> temp = runningArenasMap.get(map);
            temp.add(arena);
            runningArenasMap.put(map, temp);
        }
        if(!runningArenasList.contains(arena.getName()))
            runningArenasList.add(arena.getName());
        
        //Setup the lobby and spectator spawns
        arena.setLobby(new ArenaLobby(arenaName, zWorld));
        arena.setSpectatorBox(new ArenaSpectatorBox(arenaName, zWorld));
        
        //Starts the arena if required to
        if(start) {
            arena.start();
        }
        
        return arena;
    }
    
    /**
     * Gets a list of all the worlds that arenas have ran in.
     * 
     * NOTE: This list is primarily kept to unload and deleted worlds when the plugin gets disabled.
     * It contains ALL worlds that arenas have ran in, are running in, or will run in.
     * @return A list of ZoneWorlds
     */
    public static ArrayList<ZoneWorld> getArenaWorlds() {return arenaWorlds;}
    
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
    public static ArrayList<Arena> getRunningArenasFromMap(String map)
    {
        return runningArenasMap.get(map);
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
