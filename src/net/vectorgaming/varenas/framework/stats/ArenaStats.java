
package net.vectorgaming.varenas.framework.stats;

import java.util.ArrayList;
import java.util.HashMap;
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
