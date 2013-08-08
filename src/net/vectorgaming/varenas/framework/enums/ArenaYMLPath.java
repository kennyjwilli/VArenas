
package net.vectorgaming.varenas.framework.enums;

/**
 *
 * @author Kenny
 */
public enum ArenaYMLPath 
{
    NAME("name"),
    LOBBY_SPAWN("locations.spawns.lobby"),
    SPECTATOR_BOX_SPAWN("locations.spawns.spectator-box"),
    ARENA_SPAWNS("locations.spawns.arena"),
    ARENA_REGION("regions.arena");
    
    private final String path;
    
    ArenaYMLPath(String path)
    {
        this.path = path;
    }
}
