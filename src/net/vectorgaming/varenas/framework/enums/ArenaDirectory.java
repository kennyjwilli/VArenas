
package net.vectorgaming.varenas.framework.enums;

import java.io.File;
import net.vectorgaming.varenas.ArenaAPI;

/**
 *
 * @author Kenny
 */
public class ArenaDirectory 
{
    public static final String ARENA_FRAMEWORK_DIR = ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"framework";
    public static final String ARENA_SETTINGS_DIR = ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"settings";
    public static final String SERVER_ROOT_DIR = "";
    public static final String MAPS_DIR = SERVER_ROOT_DIR+File.separator+"maps";
    public static final String ARENAS_DIR = SERVER_ROOT_DIR+File.separator+"arenas";    
//    /**
//     * Directory for all the framework files for each map
//     */
//    ARENA_FRAMEWORK_DIR (ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"framework"),
//    /**
//     * Directory for the arena settings file for each map
//     */
//    ARENA_SETTINGS_DIR(ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"settings"),
//    /**
//     * Root directory of the server
//     */
//    SERVER_ROOT_DIR(""),
//    /**
//     * The location of the world for each map
//     */
//    MAPS_DIR (SERVER_ROOT_DIR+File.separator+"maps"),
//    /**
//     * The directory for all the running arenas
//     */
//    ARENAS_DIR (SERVER_ROOT_DIR+File.separator+"arenas");
//    
//    private final String file;
//    
//    ArenaDirectory(String f)
//    {
//        file = f;
//    }
}
