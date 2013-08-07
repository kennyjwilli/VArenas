
package net.vectorgaming.varenas.framework.config;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.RoundTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBoxEventHandler;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ArenaConfig extends ZoneConfig{
    
    private static String NAME = "name";
    private static String ARENA_BOX = "arena-box";
    private static String LOBBY_LOC = "spawns.lobby";
    private static String SPECTATOR_BOX_LOC = "spawns.spectator-box";
    private static String ARENA_SPAWN_LOC = "spawns.arena";
    private static String REFERENCE_LOCATION = "reference-location";
    
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
        this.set(NAME, name);
    }
    /**
     * Gets the name of the arena
     * @return Name of the arena
     */
    public String getArenaName()
    {
        return getString(NAME);
    }
    
    /**
     * Sets the lobby spawn in the YML file
     * @param loc Location of the spawn
     */
    public void setLobbySpawn(Location loc)
    {
        this.set(LOBBY_LOC, new Point3D(loc).toSaveString());
    }
    
    /**
     * Gets the 3D location of the spawn
     * @return Point3D
     */
    public Point3D getLobbySpawn()
    {
        return Point3D.toPoint3D(this.getString(LOBBY_LOC));
    }
    
    /**
     * Sets the spectator box location in the YML file
     * @param loc Location of the spawn
     */
    public void setSpectatorBoxSpawn(Location loc)
    {
        this.set(SPECTATOR_BOX_LOC, new Point3D(loc).toSaveString());
    }
    
    /**
     * Gets the 3D location of the spawn
     * @return Point3D
     */
    public Point3D getSpectatorBoxSpawn()
    {
        return Point3D.toPoint3D(this.getString(SPECTATOR_BOX_LOC));
    }
    
    /**
     * 
     * @param spawnName
     * @param loc
     */
    public void addArenaSpawn(String spawnName, Location loc)
    {
        this.set(ARENA_SPAWN_LOC+"."+spawnName.toLowerCase(), new Point3D(loc).toSaveString());
    }
    
    public Point3D getArenaSpawn(String spawnName)
    {
        return Point3D.toPoint3D(this.getString(ARENA_SPAWN_LOC+"."+spawnName));
    }
    
    public ArrayList<Point3D> getArenaSpawns()
    {
        ArrayList<Point3D> result = new ArrayList<>();
        for(String s : this.getConfigurationSection(ARENA_SPAWN_LOC).getKeys(false))
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
    //Setting and getting the TriggerBox defining the arena area
    public void setArenaBox(TriggerBox box){
        this.set(ARENA_BOX, box.toSaveString());
    }
    public TriggerBox getTriggerBox() throws Exception{
        return getTriggerBox(null);
    }
    public TriggerBox getTriggerBox(TriggerBoxEventHandler eventHandler) throws Exception{
        return getTriggerBoxFromString(this.getString(ARENA_BOX),eventHandler);
    }
    
    public static TriggerBox getTriggerBoxFromString(String saveString) throws Exception{
        return getTriggerBoxFromString(null);
    }
    public static TriggerBox getTriggerBoxFromString(String saveString, TriggerBoxEventHandler eventHandler) throws Exception{
        //Try as polygon (most used)
        try{
            return PolygonTriggerBox.getPolygonTriggerBox(saveString,eventHandler);
        } catch(Exception exPolygon){}
        
        //Try as circle
        try{
            return RoundTriggerBox.getRoundTriggerBox(saveString, eventHandler);
        } catch(Exception exCircle){}
        
        //None of the known triggerboxes can be applied to this string
        throw new Exception("Cannot get TriggerBox from String: "+saveString);
    }
    
    public void addLocation(String path, Location location)
    {
        this.set(path, new Point3D(location).toSaveString());
    }
    
    public void addTriggerBox(String path, TriggerBox box)
    {
        this.set(path, box.toSaveString());
    }
    
}
