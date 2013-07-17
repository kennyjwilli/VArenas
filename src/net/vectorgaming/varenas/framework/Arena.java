
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.vectorgaming.varenas.util.Msg;
import net.vectorgaming.vevents.event.VEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public abstract class Arena extends VEvent
{
    /*
     * Need to load these valuse from the config later
     */
    private boolean blockBreakEnabler = false;
    private boolean editMode = false;
    private boolean tntEnabled = false;
    private Location lobbyLoc;
    private Location spectateLoc;
    private HashMap<String, Location> spawnPoints = new HashMap<>();
    private VRegion arenaBox;
    private VRegion lobbyBox;
    private VRegion spectateBox;
    
    public Arena(String name) throws Exception
    {
        super(name);
    }

    /**
     * Specifies if blocks are able to be broken for this arena
     * @return True if blocks can be broken; false if blocks cannot be broken
     */
    public boolean isBlockBreakEnabled(){return blockBreakEnabler;}
    
    /**
     * Toggles block breaking in the arena
     * @param value Enabler for block breaking
     */
    public void setBlockBreak(boolean value){this.blockBreakEnabler = value;}
    
    public boolean isTnTEnabled() {return tntEnabled;}
    
    public void setTnTUse(boolean value) {this.tntEnabled = value;}
    
    /**
     * Checks to see if the arena is currently in edit mode. Edit mode disables the
     * arena from being able to run
     * @return True if enabled; false if disabled
     */
    public boolean isEditModeEnabled(){return editMode;}
        
    /**
     * Enables or disables edit mode for the arena
     * @param value Edit mode value
     */
    public void setEditMode(boolean value){editMode = value;}
    
    public void checkArenaSetup(Player p)
    {
        ArrayList<String> result = new ArrayList<>();
        if(getArenaBox() == null)
            result.add("Arena Region");
        if(getSpawnPoints().isEmpty())
            result.add("Arena Spawn Points");
        if(getLobbyLocation() == null)
            result.add("Lobby Location");
        if(getSpectateLocation() == null)
            result.add("Spectator Box Location");
        
        if(result.isEmpty())
            {
                Msg.sendPluginMessage(p, ChatColor.GREEN+"Arena "+getName()+" is now setup! Type "+ChatColor.YELLOW+"/arena ready "+getName()+ChatColor.GREEN+" to finish arena setup!");
            }else
            {
                boolean first = false;
                String output = "";
                
                for(String s : result)
                {
                    if(first)
                    {
                        output += ChatColor.WHITE+", ";
                    }else
                    {
                        first = true;
                    }
                    output += ChatColor.RED+s;
                }
                p.sendMessage(ChatColor.BLUE+"Arena "+ChatColor.YELLOW+getName()+ChatColor.BLUE+" is not ready for use.");
                p.sendMessage(ChatColor.DARK_RED+"Missing from Setup: "+output);
            }
    }
    
    /**
     * Gets the spawn points HashMap
     * @return A HashMap of spawn points and their locations
     */
    public HashMap<String, Location> getSpawnPointsMap(){return spawnPoints;}
    
    /**
     * Gets the spawn points for the respective arena
     * @return A list of locations for each spawn point
     */
    public ArrayList<Location> getSpawnPoints()
    {
        ArrayList<Location> result = new ArrayList<>();
        
        for(Map.Entry kv : spawnPoints.entrySet())
        {
            result.add((Location) kv.getValue());
        }
        
        return result;
    }
    
    /**
     * Gets the names of all the spawn points for the arena
     * @return ArrayList<String>
     */
    public ArrayList<String> getSpawnPointsNames()
    {
        ArrayList<String> result = new ArrayList<>();
        
        for(Map.Entry kv : spawnPoints.entrySet())
            result.add((String) kv.getKey());
        return result;
    }
    
    /**
     * Adds a spawn point to the arena
     * @param name The name of the spawn point
     * @param loc The location of the spawn point 
     */
    public void addSpawnPoint(String name, Location loc) {spawnPoints.put(name, loc);}
    
    /**
     * Deletes a spawn point from the arena
     * @param name The name of the spawn point
     */
    public boolean deleteSpawnPoint(String name) 
    {
        if(spawnPoints.containsKey(name))
        {
            spawnPoints.remove(name);
            return true;
        }
        return false;
    }
    
    /**
     * Gets the spawn point for the Lobby
     * @return Location Object
     */
    public Location getLobbyLocation(){return lobbyLoc;}
    
    /**
     * Sets the spawn point for the Lobby
     * @param loc Location Object
     */
    public void setLobbyLocation(Location loc){lobbyLoc = loc;}
    
    /**
     * Gets the spawn point for the Spectator Box
     * @return Location
     */
    public Location getSpectateLocation(){return spectateLoc;}
    
    /**
     * Sets the spawn point for the Spectator Box
     * @param loc Location
     */
    public void setSpectateLocation(Location loc){spectateLoc = loc;}
    
    /**
     * Gets the entire area the arena is located in
     * @return PolygonTriggerBox
     */
    public VRegion getArenaBox(){return arenaBox;}
    
    /**
     * Sets the area for the arena
     * @param polygon PloygonTriggerBox
     */
    public void setArenaBox(VRegion polygon){arenaBox = polygon;}
    
    /**
     * Gets the entire area the lobby is located in. This is not required in arena setup
     * and may return null. If null then the lobby is included in the arena area.
     * @return PolygonTriggerBox
     */
    public VRegion getLobbyBox(){return lobbyBox;}
    
    /**
     * Sets the area for the lobby
     * @param polygon PolygonTriggerBox
     */
    public void setLobbyBox(VRegion polygon){lobbyBox = polygon;}
    
    /**
     * Gets the entire area the lobby is located in. This is not required in arena setup
     * and may return null. If null then the Spectator Box is included in the arena area.
     * @return PolygonTriggerBox
     */
    public VRegion getSpectateBox(){return spectateBox;}
    
    /**
     * Sets the area for the Spectator Box
     * @param polygon PolygonTriggerBox
     */
    public void setSpectateBox(VRegion polygon){spectateBox = polygon;}
    
    
        
}
