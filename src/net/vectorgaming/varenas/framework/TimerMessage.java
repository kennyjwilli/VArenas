
package net.vectorgaming.varenas.framework;

import net.vectorgaming.vevents.VEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Kenny
 */
public class TimerMessage 
{
    private int TASK_ID;
    private long startDelay;
    private long repeat;
    private Plugin plugin;
    
    public TimerMessage(Plugin plugin, long startDelay, long repeat, String message)
    {
        this.plugin = plugin;
        this.startDelay = startDelay;
        this.repeat = repeat;
    }
    
    public void start()
    {
        TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            public void run()
            {
                
            }
        }, startDelay, repeat);
    }
    
    public void stop()
    {
        Bukkit.getScheduler().cancelTask(TASK_ID);
    }
    
    public int getTaskID()
    {
        return TASK_ID;
    }
}
