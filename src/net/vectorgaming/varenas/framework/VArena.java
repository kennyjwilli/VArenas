
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
public abstract class VArena extends VEvent
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
    private TriggerBox arenaBox;
    private TriggerBox lobbyBox;
    private TriggerBox spectateBox;
    private ArenaLobby lobby;
    private ArenaSpectatorBox spectatorBox;
    
    public VArena(String name, ArenaLobby lobby, ArenaSpectatorBox spectatorBox)
    {
        super(name);
    }
    
    public VArena(String name)
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
        if(this.getLobby().getSpawn() == null)
            result.add("Lobby Location");
        if(this.getSpectatorBox().getSpawn() == null)
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
    
    public HashMap<String,Location> getSpawnPointMap() {return spawnPoints;}
    
    public void setLobby(ArenaLobby lobby)
    {
        this.lobby = lobby;
    }
    
    public ArenaLobby getLobby()
    {
        return lobby;
    }
    
    public void setSpectatorBox(ArenaSpectatorBox spectatorBox)
    {
        this.spectatorBox = spectatorBox;
    }
    
    public ArenaSpectatorBox getSpectatorBox()
    {
        return this.spectatorBox;
    }
    
    /**
     * Gets the entire area the arena is located in
     * @return PolygonTriggerBox
     */
    public TriggerBox getArenaBox(){return arenaBox;}
    
    /**
     * Sets the area for the arena
     * @param polygon PloygonTriggerBox
     */
    public void setArenaBox(TriggerBox polygon){arenaBox = polygon;}
}
