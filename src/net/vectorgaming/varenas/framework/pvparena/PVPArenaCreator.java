
package net.vectorgaming.varenas.framework.pvparena;

import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.framework.ArenaCreator;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.PVPArena;

/**
 *
 * @author Kenny
 */
public class PVPArenaCreator extends ArenaCreator
{

    @Override
    public Arena getNewArenaInstance(String name, String map, ZoneWorld world)
    {
        return new PVPArena(name, map, world);
    }

    @Override
    public String getName()
    {
        return "PVP_ARENA";
    }
    
}
