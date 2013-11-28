
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import info.jeppes.ZoneCore.ZoneTools;
import info.jeppes.ZoneWorld.ZoneWorld;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.drayshak.WorldInventories.Group;
import me.drayshak.WorldInventories.WorldInventories;
import net.arcanerealm.arenasigns.ArenaSignsAPI;
import net.vectorgaming.arenakits.KitManager;
import net.vectorgaming.arenakits.framework.Kit;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.enums.EventResult;
import net.vectorgaming.varenas.framework.stats.ArenaStats;
import net.vectorgaming.varenas.framework.teams.TeamManager;
import net.vectorgaming.varenas.util.SLAPI;
import net.vectorgaming.vchat.ChatManager;
import net.vectorgaming.vchat.framework.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Kenny
 */
public abstract class Arena implements Listener
{
    private String name;
    private final String type;
    private final String map;
    private final ArrayList<Player> players = new ArrayList();
    private final ArrayList<String> allPlayers = new ArrayList<>();
    private ZoneWorld world;
    private boolean isRunning = false;
    private int id;
    private int TASK_ID;
    private int gameTime = 0;
    private HashMap<String, Location> spawnPoints = new HashMap();
    private TriggerBox arenaBox;
    private ArenaLobby lobby;
    private ArenaSpectatorBox spectatorBox;
    private ArenaStats stats;
    private Location postGameSpawn;
    private final TeamManager teamManager;
    private Kit spawnKit;
    private final Channel channel;
    
    private final HashMap<String, TriggerBox> triggerBoxMap = new HashMap<>();
    private HashMap<String, Location> locationMap = new HashMap<>();
    private final HashMap<Player, ArrayList<Channel>> oldChannels = new HashMap<>();
    
    /**
     * 
     * @param name Name of the arena
     * @param map Map the arena is based off
     * @param lobby ArenaLobby
     * @param spectatorBox ArenaSpectatorBox
     * @param world World the arena will be based in
     * @param ch Chat channel for the arena
     */
    public Arena(String name, String map, ArenaLobby lobby, ArenaSpectatorBox spectatorBox, ZoneWorld world, Channel ch)
    {
        this.name = name;
        this.type = ArenaManager.getArenaSettings(map).getType();
        this.map = map;
        this.lobby = lobby;
        this.spectatorBox = spectatorBox;
        this.world = world;
        teamManager = new TeamManager();
        locationMap = convertToLocation(ArenaManager.getAreanFramework(map).getLocationMap());
        spawnPoints = convertToLocation(ArenaManager.getAreanFramework(map).getSpawnsMap());
        if(ArenaManager.getArenaSettings(map).getPostGameSpawn().equalsIgnoreCase("{default}"))
        {
            postGameSpawn = ArenaAPI.getHubWorld().getSpawnLocation();
        }else
        {
            postGameSpawn = SLAPI.getLocationFromSave(getSettings().getPostGameSpawn());
        }
        if(getSettings().isSpawnKitEnabled())
        {
            spawnKit = KitManager.getKit(getSettings().getSpawnKitName());
        }else
        {
            spawnKit = null;
        }
        channel = ch;
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
        teamManager = new TeamManager();
        locationMap = convertToLocation(ArenaManager.getAreanFramework(map).getLocationMap());
        spawnPoints = convertToLocation(ArenaManager.getAreanFramework(map).getSpawnsMap());
        if(ArenaManager.getArenaSettings(map).getPostGameSpawn().equalsIgnoreCase("{default}"))
        {
            postGameSpawn = ArenaAPI.getHubWorld().getSpawnLocation();
        }else
        {
            postGameSpawn = SLAPI.getLocationFromSave(getSettings().getPostGameSpawn());
        }
        if(getSettings().isSpawnKitEnabled())
        {
            spawnKit = KitManager.getKit(getSettings().getSpawnKitName());
        }else
        {
            spawnKit = null;
        }
        channel = ChatManager.createChannel(name, "ARENA_CHANNEL");
    }
    
    /**
     * Get the team manager used with the respective arena
     * @return The team manager used by the arena
     */
    public TeamManager getTeamManager() 
    {
        return teamManager;
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
     * @return Name of the arena
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the name of the arena
     * @param name Name of the arena
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Gets the map the arena is based off
     * @return Map the arena is based off
     */
    public String getMap() 
    {
        return map;
    }
    
    /**
     * Gets the ID for the arena
     * @return The ID of the running arena
     */
    public Integer getId()
    {
        return id;
    }
    
    /**
     * Sets the ID for the arena
     * This method should generally not be used
     * @param id New arena ID
     */
    public void setId(int id) 
    {
        this.id = id;
    }
    
    /**
     * Starts the arena. Usually all players will be teleported to the lobby
     */
    public void start()
    {
        setRunning(true);
        stats = new ArenaStats(this);
        this.setupWorldInventory();
        world.setPVP(true);
        
        for(Player p : ArenaPlayerManager.getPlayersInArena(this)) 
        {
            addPlayer(p);
        }
        
        for(Player p : getPlayers())
        {
            if(p.getGameMode() != GameMode.SURVIVAL) 
            {
                p.setGameMode(GameMode.SURVIVAL);
            }
            
            //Teleport player to his/her spawn
            p.teleport(getSpawnLocation(p));
            
            //Give players their kits if needed
            if(KitManager.kitExists(getSettings().getSpawnKitName()))
            {
                Kit kit = ArenaPlayerManager.getKitFromPlayer(p);
                boolean clear = getSettings().isKitClearInventory();
                if(kit == getSpawnKit() && getSettings().isSpawnKitEnabled())
                {
                    kit.giveKit(p, clear);
                }
                if(getSettings().isCustomKitsEnabled())
                {
                    List<String> allowedKits = getSettings().getAllowedCustomKits();
                    if(allowedKits.isEmpty())
                    {
                        kit.giveKit(p, clear);
                    }else
                    {
                        if(allowedKits.contains(kit.getName()))
                            kit.giveKit(p, clear);
                    }
                }
            }
        }
                        
        //Starts the game timer
        TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ArenaAPI.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                if(getSettings().getGameDuration() <= 0 && getSettings().getGameDuration() - 1 == gameTime)
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
        deleteWorldInventory();
        for(Player p : world.getPlayers())
        {
            p.teleport(postGameSpawn);
            /*
            Add players back into their previous chat channels
            */
            ChatManager.leaveChannel(p, channel, true);
            if(oldChannels.containsKey(p))
            {
                for(Channel ch : oldChannels.get(p)) ChatManager.focusChannel(p, ch);
            }
        }
        ChatManager.deleteChannel(channel.getName());
        this.removeAllPlayers();
        unloadAndDeleteWorld();
    }
    
    /**
     * Checks to see if a player can join the arena. This method, by default, checks to see if the
     * player crashed out of the game in which case they can automatically join even if 
     * the max players has been reached. It also checks to see if the max amount of players 
     * will be reached if the player joins. 
     * @param p Player to check
     * @return TRUE if the player can join, FALSE if not
     */
    public boolean canJoin(Player p)
    {
        return allPlayers.contains(p.getName()) || getPlayers().size() <= getSettings().getMaxPlayers();
    }
    
    /**
     * Called when a player joins the arena
     * @param player Player who joins the arena
     */
    public void onJoin(Player player)
    {
        if(!allPlayers.contains(player.getName()))
        {
            allPlayers.add(player.getName());
        }
        ArenaAPI.resetPlayerState(player);
        player.getInventory().clear();
        ArenaPlayerManager.addPlayerToArena(getName(), player);
        ArenaSignsAPI.updateAllArenaSigns(getName());
        
        /*
        Adds players to the arena chat channel and saves their old channels
        */
        if(ChatManager.getJoinedChannels(player) != null)
        {
            ArrayList<Channel> tempList = (ArrayList<Channel>) ChatManager.getJoinedChannels(player).clone();
            oldChannels.put(player, tempList);
            for(int i = 0; i < tempList.size(); i++) ChatManager.leaveChannel(player, tempList.get(i), true);
        }
        ChatManager.focusChannel(player, getChannel());
        
        if(ArenaPlayerManager.getPlayersInArena(this).size() >= getSettings().getMinPlayers())
        {
            getLobby().startLobbyTimer();
        }
    }
    
    /**
     * Handles what happens when a player disconnects from the server when in an arena
     * 
     * By default it adds the player to the list so that they can join the game again if
     * the crashed.
     * 
     * @param event PlayerQuitEvent
     */
    public void onQuit(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        allPlayers.add(p.getName());
        getPlayers().remove(p.getPlayer());
        ArenaPlayerManager.removePlayerFromArena(getName(), p);
        ArenaSignsAPI.updateAllArenaSigns(getName());
    }
    
    /**
     * Sends all the players in the arena a message when the round is over
     */
    public abstract void sendEndMessage();
    
    /**
     * Handles what happens when the player dies in the arena
     * @param dead Player who died
     * @param killer Player who killed @param dead
     */
    public abstract void onDeath(Player dead, Entity killer);
    

    /**
     * Handles what happens to a player when the respawn. Also sets the location of where the player
     * will respawn
     * @param player Player that is respawning
     * @return Location that the player will respawn at
     */
    public abstract Location onRespawn(Player player);
    
    /**
     * Called when a player places a block and if the block place is set to true in the settings
     * @param event Bukkit event
     */
    public abstract void onBlockPlace(BlockPlaceEvent event);
    
    /**
     * Called when a player breaks a block and if the block break is set to true in the settings
     * @param event Bukkit event
     */
    public abstract void onBlockBreak(BlockBreakEvent event);
    
    /**
     * Called when an entity is damaged by another entity
     * @param event Bukkit event
     */
    public abstract void onEntityDamageByEntity(EntityDamageByEntityEvent event);
    
    /**
     * Called when a player launches a projectile
     * @param event Bukkit event
     */
    public abstract void onProjectileLaunch(ProjectileLaunchEvent event);
        
    /**
     * Removes all players from the arena
     */
    public void removeAllPlayers()
    {
        for(Player p : getPlayers())
        {
            ArenaPlayerManager.removePlayerFromArena(this.getName(), p);
        }
    }
    
    /**
     * Ends the match with all normal procedures
     */
    public void end()
    {
        setRunning(false);
        endTeleportAction();
        ArenaManager.deleteArena(this);
//        deleteWorldInventory();
        //rewardPlayers(null);
        deleteAndResetChannels();
        recordStats();
        sendEndMessage();
        removeAllPlayers();
        HandlerList.unregisterAll(this);
       
        for(Entity entity : world.getEntities())
        {
            entity.remove();
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(ArenaAPI.getPlugin(), new Runnable()
        {
            public void run()
            {
                ArenaSignsAPI.updateAllArenaSigns(getName());
                unloadAndDeleteWorld();
            }
        }, 1L);
    }
    
    private void deleteAndResetChannels()
    {
        for(Player p : getPlayers())
        {
            ChatManager.leaveChannel(p, channel, true);
            if(oldChannels.containsKey(p))
            {
                for(Channel ch : oldChannels.get(p))
                {
                    ChatManager.focusChannel(p, ch);
                }
            }
        }
        ChatManager.deleteChannel(channel.getName());
    }
    
    public void unloadAndDeleteWorld()
    {
        //world.unloadWorld();
        Bukkit.getServer().unloadWorld(world.getCraftWorld(), false);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(ArenaAPI.getPlugin(), new Runnable()
        {
            public void run()
            {
                ZoneTools.deleteDirectory(world.getWorldFolder());
            }
        }, 1L);
    }
    
    /**
     * Completely resets the arena to its default state
     * @param delay Length of time in seconds until reload should happen
     */
    public void reloadArena(int delay)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ArenaAPI.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                ArenaManager.createArena(getMap(), getName(), false);
            }
        }, delay * 20);
    }
    
    /**
     * Gets the kit the players spawn with
     * @return Kit given to the players when they spawn
     */
    public Kit getSpawnKit() 
    {
        return spawnKit;
    }
    
    /**
     * Sets the spawn kit for the arena
     * @param kit Kit given to the players when they spawn
     */
    public void setSpawnKit(Kit kit) 
    {
        spawnKit = kit;
    }
    
    /**
     * Gets the location of the post game spawn. 
     * @return Location of the post game spawn
     */
    public Location getPostGameSpawn()
    {
        return postGameSpawn;
    }
    
    /**
     * Sets the post game spawn.
     * 
     * NOTE: This value will NOT be saved to the map settings. To save the post game spawn to the
     * map use ArenaSettings.setPostGameSpawn
     * @param location Location of the post game spawn
     */
    public void setPostGameSpawn(Location location) 
    {
        postGameSpawn = location;
    }
    
    /**
     * Gets the ArenaSettings for the arena
     * @return ArenaSettings object
     */
    public final ArenaSettings getSettings() 
    {
        return ArenaManager.getArenaSettings(this);
    }
    
    /**
     * Gets the arena framework for the arena
     * @return ArenaFramework object
     */
    public final ArenaFramework getFramework() 
    {
        return ArenaManager.getArenaFramework(this);
    }
    
    /**
     * Gets the current game time in seconds
     * @return Game time in seconds
     */
    public Integer getGameTime()
    {
        return gameTime;
    }
    
    /**
     * Sets the arena running boolean value
     * @param value TRUE to set running, FALSE if not
     */
    public void setRunning(boolean value) 
    {
        isRunning = value;
    }
    
    /**
     * Gets if the arena is currently running
     * @return TRUE if is running, FALSE if not
     */
    public boolean isRunning() 
    {
        return isRunning;
    }
    
    
    /**
     * Gets the event type for the arena. This value should be in all caps and spaces should
     * be underscores
     * @return The type of arena the arena is
     */
    public String getArenaType() 
    {
        return type;
    }
    
    /**
     * Gets the stats for the arena
     * @return The stats
     */
    public ArenaStats getStats() {return stats;}
    
    public void setArenaStats(ArenaStats stats) 
    {
        this.stats = stats;
    }
            
    /**
     * Gets the result of the game
     * @return Result of the game
     */
    public EventResult getResult()
    {
        if(isRunning())
            return EventResult.GAME_RUNNING;
        return EventResult.ONE_WINNER;
    }
    
    /**
     * Gets the players in the arena
     * @return List of players in the arena
     */
    public ArrayList<Player> getPlayers(){return players;}
    
    /**
     * Adds a player directly to the arena. 
     * WARNING: To add a player the proper way ArenaManager.addPlayerToArena should be used
     * @param p Player to add
     */
    public void addPlayer(Player p)
    {
        if(!players.contains(p))
            players.add(p);
    }
    
    /**
     * Removes a player directly from the Arena
     * WARNING: To removed a player the proper way ArenaManager.removePlayerFromArena should be used
     * @param p Player to remove
     */
    public void removePlayer(Player p)
    {
        if(players.contains(p))
            players.remove(p);
    }
    
    /**
     * Gets if the player is joined to the arena
     * @param p Player to check
     * @return TRUE if the player is joined to the arena, FALSE if not
     */
    public boolean isActivePlayer(Player p)
    {
        return players.contains(p);
    }
    
    /**
     * Teleports all players to a default location
     */
    public void endTeleportAction()
    {
        //later should teleport all players to the realm gates or whatever we end up calling them
        //Location loc = Bukkit.getWorld("spawn").getSpawnLocation();
//        ZoneWorld postWorld = ZoneWorldAPI.getWorld(getPostGameSpawn().getWorld());
//        System.out.println("123");
//        if(postWorld != null)
//        {
//            System.out.println("11");
//            if(!world.isLoaded())
//            {
//                world.load();
//                System.out.println("22");
//            }
//            System.out.println("33");
//        }else
//        {
//            System.out.println("NULL POST WORLD");
//        }
        
        for(Player p : players)
        {
            ArenaAPI.resetPlayerState(p);
            p.teleport(postGameSpawn);
        }
    }
    
    /**
     * Gets the chat channel for the arena
     * @return A chat channel
     */
    public Channel getChannel()
    {
        return channel;
    }
    
    /**
     * Resets all players inventories to the state before the match
     */
    public void setupWorldInventory(){
        if(Bukkit.getPluginManager().isPluginEnabled("WorldInventories")){
            ArrayList<String> worlds = new ArrayList();
            worlds.add(getWorld().getName());
            Group group = new Group(getName() + "_" +getId(), worlds, GameMode.SURVIVAL);
            
            WorldInventories.groups.add(group);
        }
    }
    
    public void deleteWorldInventory(){
        if(Bukkit.getPluginManager().isPluginEnabled("WorldInventories")){
            Group findGroup = WorldInventories.findGroup(getWorld().getName());
            WorldInventories.groups.remove(findGroup);
        }
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
     * Gets the spawn points HashMap
     * @return A HashMap of spawn points and their locations
     */
    public HashMap<String, Location> getSpawnPointsMap()
    {
        return spawnPoints;
    }
    
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
     * @return List of spawn point names
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
     * @param point 3D location of the spawn point
     */
    public void addSpawnPoint(String name, Point3D point) 
    {
        Location loc = new Location(world, point.x, point.y, point.z);
        spawnPoints.put(name, loc);
    }
    
    /**
     * Deletes a spawn point from the arena
     * @param name The name of the spawn point
     * @return Returns if the spawn point exists in the list
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
     * @return Key: spawn point name, Value: spawn point location
     */
    public HashMap<String,Location> getSpawnPointMap() 
    {
        return spawnPoints;
    }

    /**
     * Get the world the arena is located in
     * @return The world the arena is located in
     */
    public ZoneWorld getWorld() 
    {
        return world;
    }

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
     * @return Box containing the arena
     */
    public TriggerBox getArenaBox()
    {
        return arenaBox;
    }
    
    /**
     * Sets the area for the arena
     * @param polygon Box containing the arena
     */
    public void setArenaBox(TriggerBox polygon)
    {
        arenaBox = polygon;
    }
}