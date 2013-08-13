
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Location;


/**
 *
 * @author Kenny
 */
public class ArenaSpectatorBox extends VRegion
{
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
}
