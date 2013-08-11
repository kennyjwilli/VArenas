
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.Location;
import org.bukkit.World;


/**
 *
 * @author Kenny
 */
public class ArenaSpectatorBox extends VRegion
{
    public ArenaSpectatorBox(String arena, World world)
    {
        Arena a = ArenaManager.getArena(arena);
        Point3D point = a.getFramework().getSpectatorBoxSpawn();
        this.setSpawn(new Location(world, point.x, point.y, point.z));
    }
}
