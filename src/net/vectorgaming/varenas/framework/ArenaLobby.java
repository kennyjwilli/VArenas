
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneWorld.ZoneWorld;
import java.util.ArrayList;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class ArenaLobby extends VRegion
{
    private int duration;
    private ArrayList<String> interval = new ArrayList<>();
    private int timeLeftLobby;
    private int TASK_ID;
    private boolean isLobbyTimerRunning = false;
    private ArenaSettings settings;
    private ArenaFramework framework;
    private Arena arena;
    
    public ArenaLobby(String arena, ZoneWorld world)
    {
        setupDefaultInterval();
        this.settings = ArenaManager.getArenaSettings(ArenaManager.getArena(arena));
        this.framework = ArenaManager.getArenaFramework(ArenaManager.getArena(arena));
        this.arena = ArenaManager.getArena(arena);
        
        duration = settings.getLobbyDuration();
        timeLeftLobby = duration;
        
        this.setSpawn(new Location(world.getCraftWorld(), framework.getLobbySpawn().x, framework.getLobbySpawn().y, framework.getLobbySpawn().z));
    }
    
    /**
     *
     * @param arena Arena object
     */
    public ArenaLobby(Arena arena)
    {
        Point3D point = arena.getFramework().getSpectatorBoxSpawn();
        this.setSpawn(new Location(arena.getWorld(), point.x, point.y, point.z));
    }
    
    /**
     * Gets the duration until the lobby is closed and the match starts
     * @return Integer
     */
    public int getLobbyDuration(){return duration;}
    
    /**
     * Sets how long the duration the lobby is open until the match starts
     * @param t Integer
     */
    public void setLobbyDuration(int t){duration = t;}
    
    /**
     * Gets the interval for the plugin to relay the match starting notice
     * @return ArrayList<String>
     */
    public ArrayList<String> getInterval(){return interval;}
    
    /**
     * Sets the interval for the plugin to relay the match starting notice
     * @param interval ArrayList<String>
     */
    public void setInterval(ArrayList<String> interval) {this.interval = interval;} 
    
    /**
     * Starts the timer until the match starts. 
     */
    public void startLobbyTimer()
    {
        TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ArenaAPI.getPlugin(), new Runnable()
        {
            public void run()
            {
                isLobbyTimerRunning = true;
                timeLeftLobby--;
                for(String s : interval)
                {
                    if(Integer.parseInt(s) == timeLeftLobby)
                    {
                        arena.getChannel().sendChannelMessage(s+" seconds until match starts!");
                    }
                }
                if(timeLeftLobby == 0)
                {
                    Bukkit.getScheduler().cancelTask(TASK_ID);
                    timeLeftLobby = duration;
                    isLobbyTimerRunning = false;
                    arena.start();
                }
            }
        }, 0L, 20L);
    }
    
    /**
     * Force stops the lobby timer
     */
    public void forceStopTimer()
    {
        if(Bukkit.getScheduler().isCurrentlyRunning(TASK_ID))
            Bukkit.getScheduler().cancelTask(TASK_ID);
    }
    
    /**
     * Gets the Task ID for the Lobby Timer
     * @return Integer
     */
    public Integer getLobbyTimerTaskID()
    {
        return TASK_ID;
    }
    
    /**
     * Gets if the lobby timer is still running. If true then players are still in the lobby and
     * the game has not yet started
     * @return boolean
     */
    public boolean isLobbyTimerRunning(){return isLobbyTimerRunning;}
    
    private void setupDefaultInterval()
    {
        interval.add("120");
        interval.add("60");
        interval.add("30");
        interval.add("15");
        interval.add("10");
        interval.add("5");
        interval.add("4");
        interval.add("3");
        interval.add("2");
        interval.add("1");
    }
            
}
