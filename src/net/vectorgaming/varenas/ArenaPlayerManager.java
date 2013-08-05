
package net.vectorgaming.varenas;

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
    private static HashMap<String, Player> arenas = new HashMap<>();
    
    public static void addPlayerToArena(String arena, Player p) 
    {
        arenas.put(arena, p);
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
    
    public static boolean isPlayerInArena(Player p)
    {
        if(players.containsKey(p))
            return true;
        return false;
    }
    
    public Set<Player> getAllArenaPlayers() {return players.keySet();}
    
    public Integer getTotalArenaPlayers() {return players.keySet().size();}
    
}
