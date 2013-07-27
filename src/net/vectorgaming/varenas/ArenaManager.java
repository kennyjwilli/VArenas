
package net.vectorgaming.varenas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import net.vectorgaming.varenas.framework.ArenaLobby;
import net.vectorgaming.varenas.framework.ArenaSpectatorBox;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaManager 
{
    private static HashMap<String, Arena> arenas = new HashMap<>();
    private static HashMap<Player, Arena> arenaPlayers = new HashMap<>();
    private static ArrayList<String> readyArenas = new ArrayList<>();
    //private static ArrayList<Player> arenaPlayers = new ArrayList<>();
    private static VArenas plugin;
    
    public ArenaManager(VArenas instance)
    {
        plugin = instance;
    }
    
    /**
     * Creates an arena
     * @param name String
     * @param type String
     * @return VArena
     */
    public static Arena createArena(String name, String type)
    {
        try
        {
            Arena arena = ArenaRegistration.getArenaClass(name, type);
            arenas.put(name, arena);
            arena.setLobby(new ArenaLobby());
            arena.setSpectatorBox(new ArenaSpectatorBox());
        }catch(Exception e)
        {
            Bukkit.getLogger().log(Level.SEVERE, "[VArenas] Class must extend VArena to use createArena!");
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /**
     * Gets the arena from the specified name
     * @param name String
     * @return VArena
     */
    public static Arena getArena(String name){return arenas.get(name);}
    
    public static Arena getArenaFromPlayer(Player p)
    {
        return arenaPlayers.get(p);
    }
    
    /**
     * Gets if the specified arena exists
     * NOTE: This will also return true for an arena that is in the process of being setup
     * @param name String
     * @return Boolean
     */
    public static boolean arenaExists(String name)
    {
        if(arenas.containsKey(name))
            return true;
        return false;
    }
    
    /**
     * Gets if the specified arena exists
     * NOTE: This will also return true for an arena that is in the process of being setup
     * @param arena VArena
     * @return Boolean
     */
    public static boolean arenaExists(Arena arena) {return arenaExists(arena.getName());}
    
    /**
     * Gets all the arenas that have been fully setup
     * NOTE: These are the arenas that will actually be saved when the plugin shuts down
     * @return ArrayList<String>
     */
    public static ArrayList<String> getReadyArenas() {return readyArenas;}
    
    /**
     * Gets if the specified arena is ready for use
     * @param name String
     * @return Boolean
     */
    public static boolean isArenaReady(String name)
    {
        if(readyArenas.contains(name) && !getArena(name).isEditModeEnabled())
            return true;
        return false;
    }
    
    /**
     * Gets if the specified arena is ready for use
     * @param arena VArena
     * @return Boolean
     */
    public static boolean isArenaReady(Arena arena) {return isArenaReady(arena.getName());}
    
    /**
     * Readys an arena
     * @param arena String
     */
    public static void readyArena(String arena)
    {
        if(!readyArenas.contains(arena))
            readyArenas.add(arena);
    }
    
    /**
     * Readys an arena
     * @param arena VArena
     */
    public static void readyArena(Arena arena) {readyArena(arena.getName());}
    
    /**
     * Gets all the players who are currently joined to an arena
     * @return ArrayList<Player>
     */
    public static Set<Player> getAllArenaPlayers() {return arenaPlayers.keySet();}
    
    /**
     * Gets the total amount of players who are currently in an arena
     * @return Integer
     */
    public Integer getTotalArenaPlayers() {return arenaPlayers.size();}
    
    /**
     * Adds a player to an arena
     * @param player Player
     * @param arena String
     */
    public static void addPlayerToArena(Player player, String arena)
    {
        if(arenaExists(arena) && isArenaReady(arena) && !isPlayerInArena(player))
        {
            if(!arenaPlayers.containsKey(player)) arenaPlayers.put(player, getArena(arena));
            getArena(arena).addPlayer(player);
        }
    }
        
    /**
     *Adds a player to an arena
     * @param player Player
     * @param arena VArena
     */
    public static void addPlayerToArena(Player player, Arena arena) {addPlayerToArena(player, arena.getName());}
    
    /**
     * Removes a player from an arena
     * @param player Player 
     * @param arena String
     */
    public static void removePlayerFromArena(Player player, String arena)
    {
        if(arenaExists(arena))
        {
            arenaPlayers.remove(player);
            getArena(arena).removePlayer(player);
        }
    }
    
    /**
     * Removes a player from an arena
     * @param player Player
     * @param arena VArena
     */
    public static void removePlayerFromArena(Player player, Arena arena) {removePlayerFromArena(player, arena.getName());}
    
    /**
     * Determines if a player is currently playing in an arena
     * @param player Player
     * @return Boolean
     */
    public static boolean isPlayerInArena(Player player)
    {
        for(Player p : getAllArenaPlayers())
        {
            if(p == player) return true;
        }
        return false;
    }
    
    /**
     * Converts two locations into five locations for PolygonTriggerBox
     * @param minY Location
     * @param maxY Location
     * @return ArrayList<Location>
     */
    public static ArrayList<Location> getPolygonPoints(Location minY, Location maxY)
    {
        ArrayList<Location> result = new ArrayList<>();
        double xChange = maxY.getBlockX() - minY.getBlockX();
        double yChange = maxY.getBlockY() - minY.getBlockY();
        double zChange = maxY.getBlockZ() - minY.getBlockZ();

        Location loc1 = new Location(minY.getWorld(), minY.getBlockX() + xChange, minY.getBlockY(), minY.getBlockZ());
        Location loc2 = new Location(minY.getWorld(), minY.getBlockX(), minY.getBlockY() + yChange, minY.getBlockZ());
        Location loc3 = new Location(minY.getWorld(), minY.getBlockX(), minY.getBlockY(), minY.getBlockZ() + zChange);
        result.add(minY);
        result.add(maxY);
        result.add(loc1);
        result.add(loc2);
        result.add(loc3);
        return result;
    }
    
    /**
     * Gets the VAreans plugin
     * @return VArenas
     */
    public static VArenas getVArenasPlugin()
    {
        return plugin;
    }
    
}
