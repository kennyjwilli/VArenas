
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaLobby extends VRegion
{
    private int duration;
    private ArrayList<String> interval;
    private int timeLeftLobby = duration;
    private int TASK_ID;
    private boolean isLobbyTimerRunning = false;
    
    public int getLobbyDuration(){return duration;}
    
    public void setLobbyDuration(int t){duration = t;}
    
    public ArrayList<String> getInterval(){return interval;}
    
    public void setInterval(ArrayList<String> interval) {this.interval = interval;} 
    
    public void startLobbyTimer()
    {
        //When VChat is done add sendChannelMessage method and use it here.
        TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ArenaManager.getVEventsPlugin(), new Runnable()
        {
            public void run()
            {
                isLobbyTimerRunning = true;
                timeLeftLobby--;
                for(String s : interval)
                {
                    if(Integer.parseInt(s) == timeLeftLobby)
                    {
                        //temp until VChat
                        Bukkit.broadcastMessage(s+" seconds until match starts!");
                    }
                }
                if(timeLeftLobby == 0)
                {
                    Bukkit.getScheduler().cancelTask(TASK_ID);
                    timeLeftLobby = duration;
                    isLobbyTimerRunning = false;
                }
            }
        }, 0L, 20L);
    }
    
    public boolean isLobbyTimerRunning(){return isLobbyTimerRunning;}
            
}
