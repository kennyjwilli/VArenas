
package net.vectorgaming.varenas.framework;

import java.io.File;
import net.vectorgaming.varenas.ArenaAPI;

/**
 *
 * @author Kenny
 */
public enum ArenaDirectory 
{
    /**
     * Directory for all the framework files for each map
     */
    ARENA_FRAMEWORK_DIR (ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"framework"),
    /**
     * Directory for the arena settings file for each map
     */
    ARENA_SETTINGS_DIR(ArenaAPI.getDataFolder().getAbsolutePath()+File.separator+"settings"),
    /**
     * Root directory of the server
     */
    SERVER_ROOT_DIR(""),
    /**
     * The location of the world for each map
     */
    MAPS_DIR (SERVER_ROOT_DIR+File.separator+"maps"),
    /**
     * The directory for all the running arenas
     */
    ARENAS_DIR (SERVER_ROOT_DIR+File.separator+"arenas");
    
    private final String file;
    
    ArenaDirectory(String f)
    {
        file = f;
    }
}
