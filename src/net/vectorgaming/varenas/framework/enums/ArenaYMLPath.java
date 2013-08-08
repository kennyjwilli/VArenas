
package net.vectorgaming.varenas.framework.enums;

/**
 *
 * @author Kenny
 */
public enum ArenaYMLPath 
{
    NAME("name"),
    LOBBY_SPAWN("spawns.lobby"),
    SPECTATOR_BOX_SPAWN("spawns.spectator-box"),
    ARENA_SPAWNS("spawns.arena"),
    ARENA_REGION("region.arena");
    
    private final String path;
    
    ArenaYMLPath(String path)
    {
        this.path = path;
    }
}
