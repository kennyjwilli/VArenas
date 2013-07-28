
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Kenny
 */
public class PVPArena extends Arena
{
    private boolean isRunning = false;
    private int TASK_ID;
    private int timeLeftLobby = 120;
    
    
    public PVPArena(String name, String type)
    {
        super(name, type);
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

}
