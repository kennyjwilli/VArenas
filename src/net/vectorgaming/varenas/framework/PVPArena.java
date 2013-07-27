
package net.vectorgaming.varenas.framework;

import java.util.ArrayList;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
    public void start() 
    {
        isRunning = true;
        
        for(Player p : getPlayers())
        {
            p.teleport(this.getLobby().getSpawn());
            //temp fix until VChat is done
            p.sendMessage("Arena starting in 2 minutes.");
        }
        
        //Add all players to Arena Chat Channel
        //Silence other channels
        
        this.getLobby().startLobbyTimer();
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ArenaManager.getVArenasPlugin(), new Runnable()
        {
            public void run()
            {
                if(!getLobby().isLobbyTimerRunning())
                {
                    /*
                    * Teleport all players into the arena at each spawn point
                    * Need some sort of check for if all the spawn points have been used.
                    * Maybe like a max players per arena or a random spawn point in the arena
                    */
                   int i = 0;
                   for(Player p : getPlayers())
                   {
                       if(i > getSpawnPoints().size()) i = 0;
                       p.teleport(getSpawnPoints().get(i));
                       i++;
                   }
                   //Bukkit.getScheduler().cancelTask(temp);
                }
            }
        }, 0L, 20L);
    }

    @Override
    public boolean isRunning() 
    {
        return isRunning;
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
    public void onDeath(Player p)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onRespawn(Player p)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
