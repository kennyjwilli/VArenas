/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.stats.stats;

import info.jeppes.ZoneCore.ZoneConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.vectorgaming.varenas.framework.stats.Stat;
import net.vectorgaming.varenas.framework.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

/**
 *
 * @author jeppe
 */
public class KillCounter extends Stat implements Listener{

    private HashMap<Player, ArrayList<Player>> kills = new HashMap<>(); // {Killer, All the people Killer killed}
    private HashMap<Player, ArrayList<Player>> deaths = new HashMap<>(); // {Killed player, All the people who kill the guy}
    private int totalKills;
    private boolean showAsObjective = true;
    private Objective killsObj;
    private Objective deathsObj;
    
    public KillCounter(){
        super("killcounter");
    }
    public KillCounter(String name){
        super(name);
    }
    public KillCounter(boolean showAsObjective){
        super("killcounter");
        this.showAsObjective = showAsObjective;
    }
    public KillCounter(String name, boolean showAsObjective){
        super(name);
        this.showAsObjective = showAsObjective;
    }
    
    
    @Override
    public void init() {
        if(showAsObjective){
            TeamManager teamManager = getArena().getTeamManager();
            killsObj = teamManager.getScoreboard().registerNewObjective("kills", "dummy");
            killsObj.setDisplayName("Kills");
            killsObj.setDisplaySlot(DisplaySlot.SIDEBAR);
            deathsObj = teamManager.getScoreboard().registerNewObjective("deaths", "dummy");
            deathsObj.setDisplayName("Deaths");
            deathsObj.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }
    
    /**
     * Adds a kill to the stats for an arena
     * @param killer Player who killed
     * @param dead Player who died
     */
    public void recordKill(Player killer, Player dead)
    {
        ArrayList<Player> killerKills;
        ArrayList<Player> playerDeaths;
        //Adds the killer
        if(kills.containsKey(killer)){
            killerKills = kills.get(killer);
            killerKills.add(dead);
            kills.put(killer, killerKills);
        } else {
            killerKills = new ArrayList<>();
            killerKills.add(dead);
            kills.put(killer, killerKills);
        }
        //Adds a death to a player and the player's killer
        if(deaths.containsKey(dead))
        {
            playerDeaths = deaths.get(dead);
            playerDeaths.add(killer);
            deaths.put(dead, playerDeaths);
        }else{
            playerDeaths = new ArrayList<>();
            playerDeaths.add(killer);
            deaths.put(dead, playerDeaths);
        }
        if(showAsObjective && killer instanceof Player && dead instanceof Player){
            killsObj.getScore((Player)killer).setScore(getKills(killer));
            deathsObj.getScore((Player)dead).setScore(getDeaths(dead));
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
            if(((ArrayList<Entity>) kv.getValue()).size() > highesKills)
                highesKills = ((ArrayList<Entity>) kv.getValue()).size();
        }
        return highesKills;
    }
    
    /**
     * Gets the player who has the highest kills in the arena
     * @return Player
     */
    public Player getPlayerWithHighestKills() {return getTopPlayers().get(0);}
    
    /**
     * Gets a list of players ordered by their kills
     * @return Map<Player, Integer>
     */
    public List<Player> getTopPlayers()
    {
        List<String> stringList = new ArrayList<>();
        List<Player> result = new ArrayList<>();
        for(Player p : kills.keySet())
            stringList.add(p.getName());
        
        Collections.sort(stringList, new Comparator<String>()
        {
            @Override
            public int compare(String s1, String s2)
            {
                if(kills.get(Bukkit.getPlayer(s1)).size() > kills.get(Bukkit.getPlayer(s2)).size())
                {
                    return 1;
                }else if (kills.get(Bukkit.getPlayer(s1)).size() < kills.get(Bukkit.getPlayer(s2)).size())
                    return -1;
                return 0;
            }
        });
        
        for(String s : stringList)
            result.add(Bukkit.getPlayer(s));
        return result;
    }
    
    /**
     * Gets if the player has died yet
     * @param p Player
     * @return Boolean
     */
    public boolean hasDied(Player p)
    {
        if(deaths.containsKey(p)) {
            return true;
        }
        return false;
    }
    
    /**
     * Gets if the player has kill anyone 
     * @param p Player
     * @return Boolean
     */
    public boolean hasKilledAnyone(Player p)
    {
        if(kills.containsKey(p)) {
            return true;
        }
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
    public ArrayList<Player> getKilledEntities(Player p)
    {
        return kills.get(p);
    }
    
    
    /**
     * Listens for entity death events to record kills and deaths
     * @param event
     */
//    @EventHandler(priority = EventPriority.MONITOR)
//    public void onEntityDeath(EntityDeathEvent event){
//        if(event.getEntity().getKiller() != null){
//            if(event.getEntity() instanceof Player){
//                Player killed = (Player)event.getEntity();
//                recordKill(killed.getKiller(), killed);
//            }
//        }
//    }
//    @EventHandler(priority = EventPriority.MONITOR)
//    public void onEntityDeath(EntityDamageEvent event){
//        if(event instanceof EntityDamageByEntityEvent) {
//            EntityDamageByEntityEvent eventDamage = (EntityDamageByEntityEvent) event;
//            Entity defender = eventDamage.getEntity();
//            Entity attacker = eventDamage.getDamager();
//            if(defender.isDead()){
//                
//            }
//        }
//    }
    
    @Override
    protected void stop() {
        
    }
    
    @Override
    public void save(ZoneConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void load(ZoneConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
