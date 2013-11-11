
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneWorld.ZoneWorld;
import java.util.ArrayList;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;


/**
 *
 * @author Kenny
 */
public class ArenaSpectatorBox extends VRegion
{
    private final ArrayList<String> spectators = new ArrayList<>();
    /**
     *
     * @param arena Name of the arena
     * @param world World the arena is in
     */
    public ArenaSpectatorBox(String arena, ZoneWorld world)
    {
        Arena a = ArenaManager.getArena(arena);
        Point3D point = a.getFramework().getSpectatorBoxSpawn();
        this.setSpawn(new Location(world.getCraftWorld(), point.x, point.y, point.z));
    }
    
    /**
     *
     * @param arena Arena object
     */
    public ArenaSpectatorBox(Arena arena)
    {
        Point3D point = arena.getFramework().getSpectatorBoxSpawn();
        this.setSpawn(new Location(arena.getWorld(), point.x, point.y, point.z));
    }
    
    /**
     * Adds a spectator to the spectator box
     * @param player Player to be a spectator
     * @param teleport Should the player be teleported to the spectator box spawn
     */
    public void addSpectator(String player, boolean teleport)
    {
        if(!spectators.contains(player))
        {
            spectators.add(player);
        }
        
        if(teleport)
        {
            if(Bukkit.getPlayer(player) != null)
            {
                Bukkit.getPlayer(player).teleport(getSpawn());
            }
        }
    }
    
    /**
     * Removes a player from being a spectator. This does not teleport the player to any 
     * location
     * @param player Player being removed from being a spectator
     */
    public void removeSpectator(String player)
    {
        if(spectators.contains(player))
        {
            spectators.remove(player);
        }
    }
    
    /**
     * Checks to see if the player is a spectator
     * @param player Player to check
     * @return TRUE if player is a spectator, false if not
     */
    public boolean isSpectator(String player)
    {
        return spectators.contains(player);
    }
}
