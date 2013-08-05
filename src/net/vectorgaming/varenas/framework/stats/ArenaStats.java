
package net.vectorgaming.varenas.framework.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaStats
{
    private HashMap<Player, ArrayList<Player>> kills = new HashMap<>(); // {Killer, All the people Killer killed}
    private HashMap<Player, ArrayList<Player>> deaths = new HashMap<>(); // {Killed player, All the people who kill the guy}
    private int totalKills;
    
    /**
     * Adds a kill to the stats for an arena
     * @param killer Player who killed
     * @param dead Player who died
     */
    public void recordKill(Player killer, Player dead)
    {
        ArrayList<Player> killerKills;
        ArrayList<Player> playerDeaths;
        if(kills.containsKey(killer))
        {
            //Adds the killer and his victim
            killerKills = kills.get(killer);
            killerKills.add(dead);
            kills.put(killer, killerKills);
            
            //Adds a death to a player and the player's killer
            playerDeaths = deaths.get(dead);
            playerDeaths.add(killer);
            deaths.put(dead, playerDeaths);
        }else
        {
            //Adds the killer and his victim
            killerKills = new ArrayList<>();
            killerKills.add(dead);
            kills.put(killer, killerKills);
            
            //Adds a death to a player and the player's killer
            playerDeaths = new ArrayList<>();
            playerDeaths.add(killer);
            deaths.put(dead, playerDeaths);
        }
        totalKills++;
    }
    
    /**
     * Gets the total kills for an arena
     * @return Integer
     */
    public Integer getTotalKills()
    {
        return totalKills;
    }
    
    /**
     * Gets the most amount of kills in the arena
     * @return Integer
     */
    public Integer getHighestKills()
    {
        int highesKills = 0;
        for(Map.Entry kv : kills.entrySet())
        {
            if((Integer) kv.getValue() > highesKills)
                highesKills = (Integer) kv.getValue();
        }
        return highesKills;
    }
    
    /**
     * Gets the player who has the highest kills in the arena
     * @return Player
     */
    public Player getPlayerWithHighestKills()
    {
        ArrayList<Player> result = new ArrayList<>(); 
        for(Map.Entry kv : getTopPlayers().entrySet())
        {
            result.add((Player) kv.getKey());
        }
        return result.get(0);
    }
    
    /**
     * Gets a list of players ordered by their kills
     * @return Map<Player, Integer>
     */
    public Map<Player, Integer> getTopPlayers()
    {
        List list = new LinkedList(kills.entrySet());
        
        Collections.sort(list, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map sortedMap = new LinkedHashMap();
        for(Iterator it = list.iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    
    /**
     * Gets if the player has died yet
     * @param p Player
     * @return Boolean
     */
    public boolean hasDied(Player p)
    {
        if(deaths.containsKey(p))
            return true;
        return false;
    }
    
    /**
     * Gets if the player has kill anyone 
     * @param p Player
     * @return Boolean
     */
    public boolean hasKilledAnyone(Player p)
    {
        if(kills.containsKey(p))
            return true;
        return false;
    }
    
    /**
     * Gets the total deaths for the specified player
     * @param p Player
     * @return Integer
     */
    public Integer getDeaths(Player p)
    {
        return deaths.get(p).size();
    }
    
    /**
     * Gets the total kills for the specified player
     * @param p Player
     * @return Integer
     */
    public Integer getKills(Player p)
    {
        return kills.get(p).size();
    }
    
    /**
     * Gets a list of who kill the specified player
     * @param p Player
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getWhoKilled(Player p)
    {
        return deaths.get(p);
    }
    
    /**
     * Gets who the specified player has killed
     * @param p Player
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getKilledPlayers(Player p)
    {
        return kills.get(p);
    }
}
