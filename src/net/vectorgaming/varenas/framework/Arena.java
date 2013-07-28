
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.stats.ArenaStats;
import net.vectorgaming.varenas.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Kenny
 */
public abstract class Arena
{
    /*
     * Need to load these valuse from the config later
     */
    private String name;
    private String type;
    private ArrayList<Player> players = new ArrayList<>();
    private boolean blockBreakEnabler = false;
    private boolean editMode = false;
    private boolean tntEnabled = false;
    private boolean isRunning = false;
    private int maxPlayers;
    private int TP_TASK_ID;
    private String authors;
    private String objective;
    private HashMap<String, Location> spawnPoints = new HashMap<>();
    private TriggerBox arenaBox;
    private ArenaLobby lobby;
    private ArenaSpectatorBox spectatorBox;
    private ArenaStats stats;
    
    /**
     *
     * @param name Name of arena
     * @param lobby ArenaLobby
     * @param spectatorBox ArenaSpectatorBox
     */
    public Arena(String name, String type, ArenaLobby lobby, ArenaSpectatorBox spectatorBox)
    {
        this.name = name;
        this.type = type;
        this.lobby = lobby;
        this.spectatorBox = spectatorBox;
    }
    
    /**
     * 
     * @param name Name of arena
     */
    public Arena(String name, String type)
    {
        this.name = name;
        this.type = type;
    }
    
    /**
     * Gets the name of the arena
     * @return String
     */
    public String getName(){return name;}
    
    /**
     * Sets the name of the arena
     * @param name String
     */
    public void setName(String name){this.name = name;}
    
    /**
     * Starts the arena. Usually all players will be teleported to the lobby
     */
    public void start()
    {
        setRunning(true);
        
        for(Player p : getPlayers())
        {
            p.teleport(this.getLobby().getSpawn());
            //temp fix until VChat is done
            p.sendMessage("Arena starting in "+this.getLobby().getLobbyDuration()+" seconds.");
        }
        
        //Add all players to Arena Chat Channel
        //Silence other channels
        
        this.getLobby().startLobbyTimer();
        
        TP_TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ArenaManager.getVArenasPlugin(), new Runnable()
        {
            public void run()
            {
                if(!getLobby().isLobbyTimerRunning())
                {
                    /*
                    * Teleport all players into the arena at each spawn point
                    * Need some sort of check for if all the spawn points have been used.
                    * Maybe like a max players per arena or a random spawn point in the arena
                    */
                   int i = 0;
                   for(Player p : getPlayers())
                   {
                       if(i > getSpawnPoints().size()) i = 0;
                       p.teleport(getSpawnPoints().get(i));
                       i++;
                   }
                   Bukkit.getScheduler().cancelTask(TP_TASK_ID);
                }
            }
        }, 0L, 20L);
    }
    
    /**
     * Readys the arena so that it can be used. 
     */
    public void readyArena()
    {
        stats = new ArenaStats();
        ArenaManager.readyArena(this.getName());
    }
        
    /**
     * Force stops the arena from running. No stats should be kept and inventories should be reset. There is no 
     * TP out and no rewards given
     */
    public void forceStop()
    {
        this.getLobby().forceStopTimer();
        if(Bukkit.getScheduler().isCurrentlyRunning(TP_TASK_ID))
            Bukkit.getScheduler().cancelTask(TP_TASK_ID);
        this.removeAllPlayers();
    }
    
    /**
     * Sends all the players in the arena a message when the round is over
     */
    public abstract void sendEndMessage();
    
    /**
     * Handles what happens when the player dies in the arena
     * @param event PlayerDeathEvent
     */
    public abstract void onDeath(PlayerDeathEvent event);
    
    /**
     * Handles where the player respawns after they die in the arena
     * @param event PlayerRespawnEvent
     */
    public abstract void onRespawn(PlayerRespawnEvent event);
    
    /**
     * Removes all players from the arena
     */
    public void removeAllPlayers()
    {
        for(Player p : getPlayers())
            ArenaManager.removePlayerFromArena(p, this);
    }
    
    /**
     * Ends the match with all normal procedures
     */
    public void end()
    {
        endTeleportAction();
        resetInventory();
        //rewardPlayers(null);
        recordStats();
        removeAllPlayers();
    }
    
    /**
     * Sets the arena running boolean value
     * @param value Boolean
     */
    public void setRunning(boolean value)
    {
        isRunning = value;
    }
    
    /**
     * Gets if the arena is currently running
     * @return boolean
     */
    public boolean isRunning() {return isRunning;}
    
    
    /**
     * Gets the event type for the arena. This value should be in all caps and spaces should
     * be underscores
     * @return String
     */
    public String getArenaType() {return type;}
    
    /**
     * Gets the stats for the arena
     * @return
     */
    public ArenaStats getStats()
    {
        return stats;
    }
    
    public void setArenaStats(ArenaStats stats)
    {
        this.stats = stats;
    }
            
    /**
     * Gets the result of the game
     * @return EventResult
     */
    public EventResult getResult()
    {
        if(isRunning())
            return EventResult.GAME_RUNNING;
        return EventResult.ONE_WINNER;
    }
    
    /**
     * Gets the players in the arena
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers(){return players;}
    
    /**
     * Adds a player directly to the arena. 
     * WARNING: To add a player the proper way ArenaManager.addPlayerToArena should be used
     * @param p Player
     */
    public void addPlayer(Player p)
    {
        if(!players.contains(p))
            players.add(p);
    }
    
    /**
     * Removes a player directly from the Arena
     * WARNING: To removed a player the proper way ArenaManager.removePlayerFromArena should be used
     * @param p Player
     */
    public void removePlayer(Player p)
    {
        if(players.contains(p))
            players.remove(p);
    }
    
    /**
     * Gets if the player is joined to the arena
     * @param p Player
     * @return Boolean
     */
    public boolean isActivePlayer(Player p)
    {
        if(players.contains(p))
            return true;
        return false;
    }
    
    /**
     * Teleports all players to a default location
     */
    public void endTeleportAction()
    {
        //later should teleport all players to the realm gates or whatever we end up calling them
        //Location loc = Bukkit.getWorld("spawn").getSpawnLocation();
        for(Player p : players){p.teleport(this.getLobby().getSpawn());}
    }
    
    /**
     * Resets all players inventories to the state before the match
     */
    public void resetInventory()
    {
        
    }
    
    /**
     * Records all stats from the match
     */
    public void recordStats()
    {
        
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
     * @return String
     */
    public String getAuthors() {return this.authors;}
    
    /**
     * Sets the authors for the arena map
     * @param authors String
     */
    public void setAuthors(String authors) {this.authors = authors;}
    
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
//        if(getArenaBox() == null)
//            result.add("Arena Region");
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
