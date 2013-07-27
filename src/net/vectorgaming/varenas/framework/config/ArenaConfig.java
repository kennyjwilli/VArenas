
package net.vectorgaming.varenas.framework.config;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.RoundTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBoxEventHandler;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ArenaConfig extends ZoneConfig{
    
    private static String NAME = "name";
    private static String ARENA_BOX = "arena-box";
    private static String REFERENCE_LOCATION = "reference-location";
    
    public ArenaConfig(Plugin plugin, File file) {
        super(plugin, file);
    }
    public ArenaConfig(Plugin plugin, File file, boolean loadDefaults) {
        super(plugin, file, loadDefaults);
    }
    
    //Setting and getting the arena name
    public void setArenaName(String name){
        this.set(NAME, name);
    }
    public String getArenaName(){
        return getString(NAME);
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
    
}
