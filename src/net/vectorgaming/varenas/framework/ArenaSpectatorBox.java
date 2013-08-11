
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
    public ArenaSpectatorBox(String arena, ZoneWorld world)
    {
        Arena a = ArenaManager.getArena(arena);
        Point3D point = a.getFramework().getSpectatorBoxSpawn();
        this.setSpawn(new Location(world.getCraftWorld(), point.x, point.y, point.z));
    }
}
