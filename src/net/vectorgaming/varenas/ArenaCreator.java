
package net.vectorgaming.varenas;

import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.framework.Arena;

/**
 *
 * @author Kenny
 */
public abstract class ArenaCreator 
{
    public abstract Arena getNewArenaInstance(String name, String map, ZoneWorld world);
    
    public abstract String getName();
}
