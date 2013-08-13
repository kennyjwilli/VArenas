
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
    public static final String SERVER_ROOT_DIR = new File("").getAbsolutePath();
    public static final String MAPS_DIR = SERVER_ROOT_DIR+File.separator+"maps";
    public static final String ARENAS_DIR = SERVER_ROOT_DIR+File.separator+"arenas";    
}
