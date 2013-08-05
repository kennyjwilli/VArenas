
package net.vectorgaming.varenas;

import net.vectorgaming.varenas.framework.Arena;

/**
 *
 * @author Kenny
 */
public abstract class ArenaCreator 
{
    public abstract Arena getNewArenaInstance();
    
    public abstract String getName();
}
