
package net.vectorgaming.varenas.framework;

/**
 *
 * @author Kenny
 */
public enum ArenaType 
{
    MOB_ARENA("MOB_ARENA"),
    PVP_ARENA("PVP_ARENA"),
    SURVIVAL_GAMES("SURVIVAL_GAMES"),
    TDM("TDM"),
    CTF("CTF");
    
    private final String event;
    
    ArenaType(String e)
    {
        event = e;
    }
}
