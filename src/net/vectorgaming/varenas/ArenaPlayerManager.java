
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
    
    public static void addPlayerToArena(String map, Integer id, Player p){addPlayerToArena(map+"_"+id, p);}
    
    public static void removePlayerFromArena(String arena, Player p) 
    {
        arenas.remove(arena);
        players.remove(p);
    }
    
    public static void removePlayerFromArena(String map, Integer id, Player p) {removePlayerFromArena(map+"_"+id, p);}
    
    public static Arena getArenaFromPlayer(Player p)
    {
        return ArenaManager.getArena(players.get(p));
    }
    
    public static String getArenaNameFromPlayer(Player p)
    {
        return players.get(p);
    }
    
    public static boolean isPlayerInArena(Player p)
    {
        if(players.containsKey(p))
            return true;
        return false;
    }
    
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
    
    public static ArrayList<Player> getPlayersInArena(Arena arena) {return getPlayersInArena(arena.getName());}
    
    public static Set<Player> getAllArenaPlayers() {return players.keySet();}
    
    public static Integer getTotalArenaPlayers() {return players.keySet().size();}
    
}
