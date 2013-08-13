
package net.vectorgaming.varenas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaPlayerManager 
{
    private static HashMap<Player, String> players = new HashMap<>();
    private static HashMap<String, ArrayList<Player>> arenas = new HashMap<>();
    
    /**
     * Adds a player to an arena
     * @param arena Name of the arena
     * @param p Bukkit player
     */
    public static void addPlayerToArena(String arena, Player p) 
    {
        if(arenas.containsKey(arena))
        {
            ArrayList<Player> temp = arenas.get(arena);
            if(!temp.contains(p))
                temp.add(p);
            arenas.put(arena, temp);
        }else
        {
            ArrayList<Player> temp = new ArrayList<>();
            temp.add(p);
            arenas.put(arena, temp);
        }
        players.put(p, arena);
    }
    
    /**
     * Adds a player to an arena based off a map type and an arena id
     * @param map Map name that the arena is based off
     * @param id Arena specific ID
     * @param p Bukkit player
     */
    public static void addPlayerToArena(String map, Integer id, Player p){addPlayerToArena(map+"_"+id, p);}
    
    /**
     * Removes a player from an arena
     * @param arena Name of the arena
     * @param p BNukkit player 
     */
    public static void removePlayerFromArena(String arena, Player p) 
    {
        arenas.remove(arena);
        players.remove(p);
    }
    
    /**
     * Removes a player from an arena based off a map and an arena ID
     * @param map Name of the map the arena is based off
     * @param id Arena specific ID
     * @param p Bukkit Player
     */
    public static void removePlayerFromArena(String map, Integer id, Player p) {removePlayerFromArena(map+"_"+id, p);}
    
    /**
     * Gets the arena that the player is in
     * @param p Bukkit player
     * @return Arena object
     */
    public static Arena getArenaFromPlayer(Player p)
    {
        return ArenaManager.getArena(players.get(p));
    }
    
    /**
     * Gets the arena name that the player is in 
     * @param p Bukkit player
     * @return Name of the arena
     */
    public static String getArenaNameFromPlayer(Player p)
    {
        return players.get(p);
    }
    
    /**
     * Checks to see if the specified player is in the arena
     * @param p Bukkit player
     * @return boolean value
     */
    public static boolean isPlayerInArena(Player p)
    {
        if(players.containsKey(p))
            return true;
        return false;
    }
    
    /**
     * Gets a list of all the players that are currently playing in the specified arena
     * @param arena Name of the arena
     * @return A list of Bukkit players
     */
    public static ArrayList<Player> getPlayersInArena(String arena)
    {
        for(String s : arenas.keySet())
        {
            if(s.equalsIgnoreCase(arena))
            {
                return arenas.get(s);
            }
        }
        return null;
    }
    
    /**
     * Gets a list of all the players that are currently playing in the specified arena
     * @param arena Arena object
     * @return A list of players
     */
    public static ArrayList<Player> getPlayersInArena(Arena arena) {return getPlayersInArena(arena.getName());}
    
    /**
     * Gets a list of all the players who are currently playing in an arena
     * @return A list of Bukkit Players
     */
    public static Set<Player> getAllArenaPlayers() {return players.keySet();}
    
    /**
     * Gets the total number of players who are currently in an arena
     * @return The total number of arena players
     */
    public static Integer getTotalArenaPlayers() {return players.keySet().size();}
    
}
