
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import info.jeppes.ZoneCore.ZoneTools;
import info.jeppes.ZoneWorld.ZoneWorld;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.enums.EventResult;
import net.vectorgaming.varenas.framework.stats.ArenaStats;
import net.vectorgaming.varenas.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Kenny
 */
public abstract class Arena implements Listener
{
    private String name;
    private String type;
    private String map;
    private ArrayList<Player> players = new ArrayList();
    private ZoneWorld world;
    private boolean blockBreakEnabler = false;
    private boolean editMode = false;
    private boolean tntEnabled = false;
    private boolean isRunning = false;
    private int maxPlayers;
    private int id;
    private int TASK_ID;
    private int gameTime = 0;
    private String authors;
    private String objective;
    private HashMap<String, Location> spawnPoints = new HashMap();
    private TriggerBox arenaBox;
    private ArenaLobby lobby;
    private ArenaSpectatorBox spectatorBox;
    private ArenaStats stats;
    
    private HashMap<String, TriggerBox> triggerBoxMap = new HashMap<>();
    private HashMap<String, Location> locationMap = new HashMap<>();
    
    /**
     * 
     * @param name Name of the arena
     * @param map Map the arena is based off
     * @param lobby ArenaLobby
     * @param spectatorBox ArenaSpectatorBox
     * @param world World the arena will be based in
     */
    public Arena(String name, String map, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, ZoneWorld world)
    {
        this.name = name;
        this.type = ArenaManager.getArenaSettings(map).getType();
        this.map = map;
        this.lobby = lobby;
        this.spectatorBox = spectatorBox;
        this.world = world;
        locationMap = convertToLocation(ArenaManager.getAreanFramework(map).getLocationMap());
        spawnPoints = convertToLocation(ArenaManager.getAreanFramework(map).getSpawnsMap());
    }
    
    /**
     * 
     * @param name Name of the arena
     * @param map Map the arena is based off 
     * @param world World the arena will be based in
     */
    public Arena(String name, String map, ZoneWorld world)
    {
        this.name = name;
        this.type = ArenaManager.getArenaSettings(map).getType();
        this.map = map;
        this.world = world;
        locationMap = convertToLocation(ArenaManager.getAreanFramework(map).getLocationMap());
        spawnPoints = convertToLocation(ArenaManager.getAreanFramework(map).getSpawnsMap());
    }
    
    private HashMap<String,Location> convertToLocation(HashMap<String,Point3D> map)
    {
        HashMap<String, Location> result = new HashMap<>();
        for(String s : map.keySet())
        {
            Point3D point = map.get(s);
            result.put(s, new Location(world.getCraftWorld(), point.x, point.y, point.z));
        }
        return result;
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
    
    public String getMap() {return map;}
    
    public Integer getId() {return id;}
    
    public void setId(Integer id) {this.id = id;}
    
    /**
     * Starts the arena. Usually all players will be teleported to the lobby
     */
    public void start()
    {
        setRunning(true);
        stats = new ArenaStats(this);
        
        for(Player p : ArenaPlayerManager.getPlayersInArena(this))
            addPlayer(p);
        
        for(Player p : getPlayers())
        {
            if(p.getGameMode() != GameMode.SURVIVAL)
                p.setGameMode(GameMode.SURVIVAL);
            p.teleport(this.getLobby().getSpawn());
            //temp fix until VChat is done
            p.sendMessage("Arena starting in "+this.getLobby().getLobbyDuration()+" seconds.");
        }
        
        //Add all players to Arena Chat Channel
        //Silence other channels
        
        this.getLobby().startLobbyTimer();
                
        //Teleports all players into game once lobby duration is complete
        TASK_ID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(ArenaAPI.getPlugin(), new Runnable()
        {
            public void run()
            {
                if(gameTime == getLobby().getLobbyDuration())
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
                   world.setPVP(true);
                }
                
                if(getSettings().getGameDuration() <= 0 && getSettings().getGameDuration() == gameTime)
                {
                    end();
                }
                
                gameTime++;
            }
        }, 0L, 20L);
    }
        
    /**
     * Force stops the arena from running. No stats should be kept and inventories should be reset. There is no 
     * TP out and no rewards given
     */
    public void forceStop()
    {
        this.getLobby().forceStopTimer();
        Bukkit.getScheduler().cancelTask(TASK_ID);
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
    public abstract void onDeath(Player dead, Entity killer);
    
    /**
     * Handles where the player respawns after they die in the arena
     * @param event PlayerRespawnEvent
     */
    public abstract void onRespawn(PlayerRespawnEvent event);
    
    /**
     * Handles what happens when a player diconnects from the server
     * @param event PlayerQuitEvent
     */
    public abstract void onQuit(PlayerQuitEvent event);
        
    /**
     * Removes all players from the arena
     */
    public void removeAllPlayers()
    {
        for(Player p : getPlayers())
        {
            //ArenaPlayerManager.
        }
    }
    
    /**
     * Ends the match with all normal procedures
     */
    public void end()
    {
        setRunning(false);
        endTeleportAction();
        resetInventory();
        //rewardPlayers(null);
        recordStats();
        removeAllPlayers();
        HandlerList.unregisterAll(this);
        world.unloadWorld();
        ZoneTools.deleteDirectory(world.getWorldFolder());
    }
    
    public ArenaSettings getSettings() {return ArenaManager.getArenaSettings(this);}
    
    public ArenaFramework getFramework() {return ArenaManager.getArenaFramework(this);}
    
    /**
     * Gets the current game time in seconds
     * @return Game time in seconds
     */
    public Integer getGameTime(){return gameTime - getLobby().getLobbyDuration();}
    
    /**
     * Sets the arena running boolean value
     * @param value Boolean
     */
    public void setRunning(boolean value) {isRunning = value;}
    
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
    public ArenaStats getStats() {return stats;}
    
    public void setArenaStats(ArenaStats stats) {this.stats = stats;}
            
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
        //Still haven't decided how the file system will be set up
        //stats.save(null);
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
     * Get the spawn point for a player
     * 
     * This method only support FFA arena types, but can be overriden to support
     * teams
     * 
     * @param player The player that will be spawned
     * @return A location either predetermined with single or set of possible spawn locations.
     * If @getSpawnPoints() is empty, it will return a random location inside the arena
     */
    public Location getSpawnLocation(Player player)
    {
        ArrayList<Location> spawnLocations = getSpawnPoints();
        if(spawnLocations != null && !getSpawnPointMap().isEmpty()){
            Location spawnLocation = spawnLocations.get((int)(Math.random() * (double)spawnLocations.size()));
            return spawnLocation;
        } else {
            TriggerBox arenaArea = this.getArenaBox();
            return arenaArea.getRandomLocationInsideBox();
        }
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
    public void addSpawnPoint(String name, Point3D point) 
    {
        Location loc = new Location(world, point.x, point.y, point.z);
        spawnPoints.put(name, loc);
    }
    
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
     * Get the world the arena is located in
     * @return The world the arena is located in
     */
    public ZoneWorld getWorld() {return world;}

    /**
     * Sets the world the arena is placed in
     * This method should generally not be used
     * @param world the new world the arena is located in
     */
    public void setWorld(ZoneWorld world) {this.world = world;}
    
    /**
     * Sets the arena lobby object
     * @param lobby ArenaLobby
     */
    public void setLobby(ArenaLobby lobby) {this.lobby = lobby;}
    
    /**
     * Get the arena lobby object
     * @return ArenaLobby
     */
    public ArenaLobby getLobby() {return lobby;}
    
    /**
     * Sets the arena spectator box 
     * @param spectatorBox ArenaSpectatorBox
     */
    public void setSpectatorBox(ArenaSpectatorBox spectatorBox) {this.spectatorBox = spectatorBox;}
    
    /**
     * Gets the spectator box object for the arena
     * @return ArenaSpectatorBox
     */
    public ArenaSpectatorBox getSpectatorBox(){return this.spectatorBox;}
    
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
