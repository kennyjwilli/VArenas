
package net.vectorgaming.varenas.framework.enums;

import java.io.File;
import net.vectorgaming.varenas.ArenaAPI;

/**
 *
 * @author Kenny
 */
public class ArenaDirectory 
{
    public static final String ARENA_FRAMEWORK = ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"framework";
    public static final String ARENA_SETTINGS = ArenaAPI.getDataFolder().getAbsolutePath().toString()+File.separator+"settings";
    public static final String SERVER_ROOT = new File("").getAbsolutePath();
    public static final String MAPS = SERVER_ROOT+File.separator+"maps";
    public static final String ARENAS = SERVER_ROOT+File.separator+"arenas";
    public static final String KITS = ArenaAPI.getDataFolder().getAbsolutePath()+File.separator+"kits";
}
