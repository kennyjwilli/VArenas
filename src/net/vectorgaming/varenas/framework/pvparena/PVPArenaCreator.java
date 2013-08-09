
package net.vectorgaming.varenas.framework.pvparena;

import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.ArenaCreator;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.PVPArena;

/**
 *
 * @author Kenny
 */
public class PVPArenaCreator extends ArenaCreator
{

    @Override
    public Arena getNewArenaInstance(String name, ZoneWorld world)
    {
        return new PVPArena(name, "PVP_ARENA", world);
    }

    @Override
    public String getName()
    {
        return "PVP_ARENA";
    }
    
}
