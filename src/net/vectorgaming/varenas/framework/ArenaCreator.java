
package net.vectorgaming.varenas.framework;

import info.jeppes.ZoneWorld.ZoneWorld;
import net.vectorgaming.varenas.framework.Arena;

/**
 *
 * @author Kenny
 */
public abstract class ArenaCreator 
{
    /**
     * Gets a new instance of an Arena object
     * @param name Name of the arena
     * @param map The map the arena is based off
     * @param world The world the arena will be in
     * @return Arena object
     */
    public abstract Arena getNewArenaInstance(String name, String map, ZoneWorld world);
    
    /**
     * Gets the name of the arena
     * @return Name of the arena
     */
    public abstract String getName();
}
