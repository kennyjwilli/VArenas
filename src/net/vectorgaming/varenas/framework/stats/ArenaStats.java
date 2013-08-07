
package net.vectorgaming.varenas.framework.stats;

import info.jeppes.ZoneCore.ZoneConfig;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.vectorgaming.varenas.framework.Arena;

/**
 *
 * @author Kenny
 */
public class ArenaStats
{
    private HashMap<String, Stat> stats = new HashMap();
    private Arena arena;
    public ArenaStats(Arena arena){
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }
    
    public Stat getStat(String statName){
        return stats.get(statName.toLowerCase());
    }
    public HashMap<String,Stat> getStatsMap(){
        return stats;
    }
    public Collection<Stat> getStatsList(){
        return stats.values();
    }
    public Set<String> getStatsNames(){
        return stats.keySet();
    }
    public void addStat(Stat stat){
        stat.setArena(getArena());
        stats.put(stat.getName().toLowerCase(), stat);
    }
    public boolean removeStat(Stat stat){
        return removeStat(stat.getName());
    }
    public boolean removeStat(String statName){
        return stats.remove(statName) == null ? false : true;
    }
    
    public void save(ZoneConfig config){
        for(Stat stat : stats.values()){
            stat.save(config);
        }
    }
}
