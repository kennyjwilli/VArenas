
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.HashMap;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class ArenaFramework 
{
    private HashMap<String, TriggerBox> triggerBoxMap = new HashMap<>();
    private HashMap<String, Point3D> locationMap = new HashMap<>();
    
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
     * Adds a location to an arena
     * @param path Path to be saved in the YML file
     * @param loc Location 
     */
    public void addLocation(String path, Location loc)
    {
        locationMap.put(path, new Point3D(loc));
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
     * Gets the HashMap containing the path for each TriggerBox
     * @return HashMap<String, TriggerBox>
     */
    public HashMap<String, TriggerBox> getTriggerBoxMap()
    {
        return triggerBoxMap;
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
