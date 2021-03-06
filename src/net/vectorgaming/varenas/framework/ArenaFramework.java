
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.vectorgaming.varenas.framework.enums.ArenaYMLPath;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class ArenaFramework 
{
    private final HashMap<String, TriggerBox> triggerBoxMap = new HashMap<>();
    private final HashMap<String, Point3D> locationMap = new HashMap<>();
    private final HashMap<String, Point3D> arenaSpawnsMap = new HashMap<>();
    private final String name;
    
    /**
     * 
     * @param map Name of the map
     */
    public ArenaFramework(String map)
    {
        name = map;
    }
    
    /**
     * Gets the name of the arena
     * @return Name of the arena
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Adds a spawn to the arena
     * @param spawnName Name of the spawn
     * @param loc Location of the spawn
     */
    public void addArenaSpawn(String spawnName, Location loc)
    {
        addArenaSpawn(spawnName, new Point3D(loc));
    }
    
    /**
     * Adds a spawn to the arena
     * @param spawnName Name of the spawn
     * @param point 3D location of the spawn
     */
    public void addArenaSpawn(String spawnName, Point3D point)
    {
        arenaSpawnsMap.put(spawnName, point);
        locationMap.put(ArenaYMLPath.ARENA_SPAWNS+"."+spawnName, point);
    }
    
    /**
     * Deletes a spawn from the arena
     * @param spawnName Name of the spawn
     * @return Boolean value if spawn exists or not
     */
    public boolean deleteSpawn(String spawnName)
    {
        if(!arenaSpawnsMap.containsKey(spawnName))
            return false;
        arenaSpawnsMap.remove(spawnName);
        locationMap.remove(ArenaYMLPath.LOBBY_SPAWN+"."+spawnName);
        return true;
    }
    
    /**
     * Gets the 3D location of a spawn point
     * @param spawnName Name of the spawn point
     * @return Point3D
     */
    public Point3D getSpawnLocation(String spawnName)
    {
        return arenaSpawnsMap.get(spawnName);
    }
    
    /**
     * Gets a list of all the locations of the spawns for the arena
     * @return ArrayList<Point3D>
     */
    public ArrayList<Point3D> getSpawns()
    {
        ArrayList<Point3D> result = new ArrayList<>();
        for(Map.Entry kv : arenaSpawnsMap.entrySet())
            result.add((Point3D)kv.getValue());
        return result;
    }
    
    /**
     * Gets a list of all the names of the created spawns
     * @return
     */
    public ArrayList<String> getSpawnNames()
    {
        ArrayList<String> result = new ArrayList<>();
        for(String s : arenaSpawnsMap.keySet())
            result.add(s);
        return result;
    }
    
    /**
     * Gets the HashMap containing all the spawn names and their respective locations
     * @return HashMap<String,Point3D>
     */
    public HashMap<String,Point3D> getSpawnsMap()
    {
        return arenaSpawnsMap;
    }
    
    /**
     * Gets the 3D location of the lobby spawn for the specified arena
     * @return Point3D
     */
    public Point3D getLobbySpawn()
    {
        return locationMap.get(ArenaYMLPath.LOBBY_SPAWN);
    }
    
    /**
     * Sets the lobby spawn for the arena
     * @param loc Location of the spawn
     */
    public void setLobbySpawn(Location loc)
    {
        setLobbyLocation(new Point3D(loc));
    }
    
    /**
     * Sets the lobby spawn for the arena
     * @param point 3D location of the spawn
     */
    public void setLobbyLocation(Point3D point)
    {
        locationMap.put(ArenaYMLPath.LOBBY_SPAWN, point);
    }
    
    /**
     * Gets the location of the specator box spawn
     * @return Point3D
     */
    public Point3D getSpectatorBoxSpawn()
    {
        return locationMap.get(ArenaYMLPath.SPECTATOR_BOX_SPAWN);
    }
    
    /**
     * Sets the spectator box spawn
     * @param loc Location of the spawn
     */
    public void setSpectatorBoxSpawn(Location loc)
    {
        setSpectatorBoxSpawn(new Point3D(loc));
    }
    
    /**
     * Sets the spectator box spawn
     * @param point 3D location of the spawn
     */
    public void setSpectatorBoxSpawn(Point3D point)
    {
        locationMap.put(ArenaYMLPath.SPECTATOR_BOX_SPAWN, point);
    }
    
    /**
     * Sets the trigger box for the arena
     * @param box TriggerBox
     */
    public void setArenaTriggerBox(TriggerBox box)
    {
        addTriggerBox(ArenaYMLPath.ARENA_REGION, box);
    }
    
    /**
     * Gets the arena's trigger box
     * @return TriggerBox
     */
    public TriggerBox getArenaTriggerBox()
    {
        return getTriggerBox(ArenaYMLPath.ARENA_REGION.toString());
    }
    
    /**
     * Adds a trigger box to the given path
     * @param path Path to be saved in the YML file
     * @param box TriggerBox
     */
    public void addTriggerBox(String path, TriggerBox box)
    {
        triggerBoxMap.put(path, box);
    }
    
    /**
     * Removes a trigger box 
     * @param path Path saved in the YML file
     */
    public void removeTriggerBox(String path)
    {
        triggerBoxMap.remove(path);
    }
    
    /**
     * Gets a trigger box from the given path
     * @param path Path saved in the YML file
     * @return TriggerBox
     */
    public TriggerBox getTriggerBox(String path)
    {
        return triggerBoxMap.get(path);
    }
    
    /**
     * Gets the HashMap containing the path for each TriggerBox
     * @return HashMap<String, TriggerBox>
     */
    public HashMap<String, TriggerBox> getTriggerBoxMap()
    {
        return triggerBoxMap;
    }
    
    /**
     * Adds a location to an arena
     * @param path Path to be saved in the YML file
     * @param point 3D point to add
     */
    public void addLocation(String path, Point3D point)
    {
        locationMap.put(path, point);
    }
    
    /**
     * Removed a location from the specified path
     * @param path Path saved in the YML file
     */
    public void removeLocation(String path)
    {
        locationMap.remove(path);
    }
    
    /**
     * Gets the 3D point of a saved location
     * @param path Path saved in the YML file
     * @return Point3D
     */
    public Point3D getPoint3D(String path)
    {
        return locationMap.get(path);
    }
    
    /**
     * Gets the HashMap containing the path for each location saved in the arena
     * @return HashMap<String, Point3D>
     */
    public HashMap<String, Point3D> getLocationMap()
    {
        return locationMap;
    }
}
