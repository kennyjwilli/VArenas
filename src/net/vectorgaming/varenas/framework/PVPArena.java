
package net.vectorgaming.varenas.framework;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
    public void onDeath(PlayerDeathEvent event)
    {
        Player dead = event.getEntity();
        Player killer = dead.getKiller();
        this.getStats().recordKill(killer, dead);
        //if(this.getStats().getHighestKills() == )
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

}
