/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vectorgaming.varenas.framework.stats;

import info.jeppes.ZoneCore.ZoneConfig;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 *
 * @author jeppe
 */
public abstract class Stat implements Listener{
    private final String name;
    private Arena arena = null;
    public Stat(String name){
        this.name = name;
    }

    public void setArena(Arena arena){
        this.arena = arena;
    }
    public Arena getArena(){
        return arena;
    }
    
    public String getName() {
        return name;
    }
    
    public void endStatMonitoring(){
        HandlerList.unregisterAll(this);
        stop();
    }
    
    protected abstract void stop();
    public abstract void save(ZoneConfig config);
    public abstract void load(ZoneConfig config);
}
