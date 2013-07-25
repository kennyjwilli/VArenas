
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
    private int maxPlayers;
    private ArrayList<String> authors;
    private String objective;
    private HashMap<String, Location> spawnPoints = new HashMap<>();
    private TriggerBox arenaBox;
    private ArenaLobby lobby;
    private ArenaSpectatorBox spectatorBox;
    
    /**
     *
     * @param name Name of arena
     * @param lobby ArenaLobby
     * @param spectatorBox ArenaSpectatorBox
     */
    public VArena(String name, ArenaLobby lobby, ArenaSpectatorBox spectatorBox)
    {
        super(name);
    }
    
    /**
     * 
     * @param name Name of arena
     */
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
    
    /**
     * Gets if TNT is enabled for the arena
     * @return boolean value
     */
    public boolean isTNTEnabled() {return tntEnabled;}
    
    /**
     * Sets the use of TNT in the arena
     * @param value boolean value
     */
    public void setTnTUse(boolean value) {this.tntEnabled = value;}
    
    /**
     * Gets the max amount of players for the arena
     * @return Integer
     */
    public Integer getMaxPlayers() {return maxPlayers;}
    
    /**
     * Sets the max amount of players for the arena
     * @param maxPlayers Integer
     */
    public void setMaxPlayers(int maxPlayers) {this.maxPlayers = maxPlayers;}
    
    /**
     * Gets the authors for the arena map
     * @return ArrayList<String>
     */
    public ArrayList<String> getAuthors() {return this.authors;}
    
    /**
     * Sets the authors for the arena map
     * @param authors ArrayList<String>
     */
    public void setAuthors(ArrayList<String> authors) {this.authors = authors;}
    
    /**
     * Gets the objective for the arena.
     * @return String
     */
    public String getObjective() {return objective;}
    
    /**
     * Sets the objective for the arena
     * @param objective String
     */
    public void setObjective(String objective) {this.objective = objective;}
    
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
    
    /**
     * A function used by the plugin to tell the player what elements are missing
     * before the arena can be ready
     * @param p Player object to send the messages to
     */
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
    
    /**
     * Gets the HashMap of spawnpoints and their location
     * @return HashMap<String, Location>
     */
    public HashMap<String,Location> getSpawnPointMap() {return spawnPoints;}
    
    /**
     * Sets the arena lobby object
     * @param lobby ArenaLobby
     */
    public void setLobby(ArenaLobby lobby)
    {
        this.lobby = lobby;
    }
    
    /**
     * Get the arena lobby object
     * @return ArenaLobby
     */
    public ArenaLobby getLobby()
    {
        return lobby;
    }
    
    /**
     * Sets the arena spectator box 
     * @param spectatorBox ArenaSpectatorBox
     */
    public void setSpectatorBox(ArenaSpectatorBox spectatorBox)
    {
        this.spectatorBox = spectatorBox;
    }
    
    /**
     * Gets the spectator box object for the arena
     * @return ArenaSpectatorBox
     */
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
