
package net.vectorgaming.varenas.framework.config;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.RoundTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBoxEventHandler;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import net.vectorgaming.varenas.framework.enums.ArenaYMLPath;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ArenaConfig extends ZoneConfig{
    
    public ArenaConfig(Plugin plugin, File file) {
        super(plugin, file);
    }
    public ArenaConfig(Plugin plugin, File file, boolean loadDefaults) {
        super(plugin, file, loadDefaults);
    }
    
    
    /**
     * Sets the name of the arena
     * @param name String name of the arena
     */
    public void setArenaName(String name)
    {
        this.set("name", name);
    }
    /**
     * Gets the name of the arena
     * @return Name of the arena
     */
    public String getArenaName()
    {
        return getString("name");
    }
    
    /**
     * Sets the lobby spawn in the YML file
     * @param loc Location of the spawn
     */
    public void setLobbySpawn(Location loc)
    {
        setLobbySpawn(new Point3D(loc));
    }
    
    /**
     * Sets the lobby spawn in the YML file
     * @param point 3D point of the location
     */
    public void setLobbySpawn(Point3D point)
    {
        this.set(ArenaYMLPath.LOBBY_SPAWN, point.toSaveString());
    }
    
    /**
     * Gets the 3D location of the spawn
     * @return Point3D
     */
    public Point3D getLobbySpawn()
    {
        return Point3D.toPoint3D(this.getString(ArenaYMLPath.LOBBY_SPAWN));
    }
    
    /**
     * Sets the spectator box location in the YML file
     * @param loc Location of the spawn
     */
    public void setSpectatorBoxSpawn(Location loc)
    {
        setSpectatorBoxSpawn(new Point3D(loc));
    }
    
    /**
     * Sets the spectator box location in the YML file
     * @param point 3D point of the location
     */
    public void setSpectatorBoxSpawn(Point3D point)
    {
        this.set(ArenaYMLPath.SPECTATOR_BOX_SPAWN, point.toSaveString());
    }
    
    /**
     * Gets the 3D location of the spawn
     * @return Point3D
     */
    public Point3D getSpectatorBoxSpawn()
    {
        return Point3D.toPoint3D(getString(ArenaYMLPath.SPECTATOR_BOX_SPAWN));
    }
    
    /**
     * Adds a spawn to the arena framework config
     * @param spawnName Name of the spawn
     * @param loc Location of the spawn
     */
    public void addArenaSpawn(String spawnName, Location loc)
    {
        addArenaSpawn(spawnName, new Point3D(loc));
    }
    
    /**
     * Adds a spawn to the arena framework config
     * @param spawnName Name of the spawn
     * @param point 3D location of the spawn
     */
    public void addArenaSpawn(String spawnName, Point3D point)
    {
        this.set(""+ArenaYMLPath.ARENA_SPAWNS+"."+spawnName.toLowerCase(), point.toSaveString());
    }
    
    /**
     * Gets the 3D location of the arena spawn
     * @param spawnName Name of the spawn
     * @return 3D location
     */
    public Point3D getArenaSpawn(String spawnName)
    {
        return Point3D.toPoint3D(this.getString(ArenaYMLPath.ARENA_SPAWNS+"."+spawnName));
    }
    
    /**
     * Gets a list of all the spawns for the arena
     * @return List of 3D points
     */
    public ArrayList<Point3D> getArenaSpawns()
    {
        ArrayList<Point3D> result = new ArrayList<>();
        for(String s : this.getConfigurationSection(ArenaYMLPath.ARENA_SPAWNS).getKeys(false))
        {
            result.add(Point3D.toPoint3D(s));
        }
        return result;
    }
    
//    //This location is used as a reference for every other location in the arena
//    public void setArenaReferenceLocation(Location location){
//        setArenaReferenceLocation(new Point3D(location));
//    }
//    public void setArenaReferenceLocation(Point3D point){
//        this.set(REFERENCE_LOCATION, point.toSaveString());
//    }
//    public Point3D getReferenceLocation(){
//        String referenceLocationString = this.getString(REFERENCE_LOCATION);
//        if(referenceLocationString != null){
//            return Point3D.toPoint3D(referenceLocationString);
//        }
//        return null;
//    }
//    
    
    /**
     * Sets the arena box
     * @param box TriggerBox
     */
    public void setArenaBox(TriggerBox box)
    {
        this.set(ArenaYMLPath.ARENA_REGION+"", box.toSaveString());
    }
    
    /**
     * Gets the arena's TriggerBox
     * @return TriggerBox
     * @throws Exception
     */
    public TriggerBox getArenaBox() throws Exception
    {
        return getTriggerBoxFromString(this.getString(""+ArenaYMLPath.ARENA_REGION));
    }
    
    /**
     * Gets the arena's TriggerBox and sets its event handler
     * @param eventHandler Event handler for the arena TriggerBox
     * @return
     * @throws Exception
     */
    public TriggerBox getArenaBox(TriggerBoxEventHandler eventHandler) throws Exception
    {
        return getTriggerBoxFromString(this.getString(""+ArenaYMLPath.ARENA_REGION), eventHandler);
    }
    
    /**
     * Sets a trigger box to be saved into the arena framework
     * @param path Path in the YML to be saved
     * @param box TriggerBox
     */
    public void addTriggerBox(String path, TriggerBox box)
    {
        this.set(path, box.toSaveString());
    }
    
    /**
     * Gets the TriggerBox from the specified path
     * @param path Path saved in the YML
     * @return TriggerBox
     * @throws Exception
     */
    public TriggerBox getTriggerBox(String path) throws Exception
    {
        return getTriggerBoxFromString(this.getString(path));
    }
    
    /**
     * Gets the TriggerBox from the spcified path and sets the triggerbox event handler
     * @param path Path saved in the YML
     * @param eventHandler TriggerBoxEventHandler
     * @return TriggerBox
     * @throws Exception
     */
    public TriggerBox getTriggerBox(String path, TriggerBoxEventHandler eventHandler) throws Exception
    {
        return getTriggerBoxFromString(this.getString(path), eventHandler);
    }
    
    /**
     * Gets a TriggerBox from its save string
     * @param saveString TriggerBox save string
     * @return TriggerBox
     * @throws Exception
     */
    public static TriggerBox getTriggerBoxFromString(String saveString) throws Exception{
        try
        {
            return PolygonTriggerBox.getPolygonTriggerBox(saveString);
        } catch(Exception exPolygon){}
        
        //Try as circle
        try
        {
            return RoundTriggerBox.getRoundTriggerBox(saveString);
        } catch(Exception exCircle){}
        
        throw new Exception("Cannot get TriggerBox from String: "+saveString);
    }
    /**
     * Gets a TriggerBox from its save string and sets its event handler
     * @param saveString TriggerBox save string
     * @param eventHandler TriggerBoxEventHandler
     * @return TriggerBox
     * @throws Exception
     */
    public static TriggerBox getTriggerBoxFromString(String saveString, TriggerBoxEventHandler eventHandler) throws Exception{
        //Try as polygon (most used)
        try
        {
            return PolygonTriggerBox.getPolygonTriggerBox(saveString,eventHandler);
        } catch(Exception exPolygon){}
        
        //Try as circle
        try
        {
            return RoundTriggerBox.getRoundTriggerBox(saveString, eventHandler);
        } catch(Exception exCircle){}
        
        //None of the known triggerboxes can be applied to this string
        throw new Exception("Cannot get TriggerBox from String: "+saveString);
    }
    
    /**
     * Adds a location to the arena
     * @param path Path to save the location
     * @param location Location of the spawn
     */
    public void addLocation(String path, Location location)
    {
        addLocation(path, new Point3D(location));
    }
    
    /**
     * Adds a location to the arena
     * @param path Path to save the location
     * @param point 3D location of the spawn
     */
    public void addLocation(String path, Point3D point)
    {
        this.set(path, point.toSaveString());
    }
}
