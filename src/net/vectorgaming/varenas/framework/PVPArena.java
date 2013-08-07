
package net.vectorgaming.varenas.framework;

import net.vectorgaming.varenas.framework.stats.stats.KillCounter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Kenny
 */
public class PVPArena extends TeamArena
{
    private boolean isRunning = false;
    private int TASK_ID;
    private int timeLeftLobby = 120;
    
    
    public PVPArena(String name, String type, World world)
    {
        super(name, type, world);
    }
    
    @Override
    public void start(){
        super.start();
        KillCounter killCounter = new KillCounter();
        getStats().addStat(killCounter);
    }
    
    @Override
    public void forceStop() 
    {
        Bukkit.getScheduler().cancelTask(TASK_ID);
        isRunning = false;
    }

    @Override
    public void sendEndMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onRespawn(PlayerRespawnEvent event)
    {
        
    }

    @Override
    public void onQuit(PlayerQuitEvent event)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDeath(PlayerDeathEvent event) {
    }

}
