
package net.vectorgaming.varenas.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.VArenas;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.Location;

/**
 *
 * @author Kenny
 */
public class SLAPI 
{
    private VArenas plugin;
    
    public SLAPI(VArenas instance)
    {
        plugin = instance;
    }
    public void saveAllArenas()
    {
        List<String> enabledArenas = new ArrayList<>();
        
        for(String s : ArenaManager.getReadyArenas())
        {
            Arena arena = ArenaManager.getArena(s);
            ZoneConfig config = new ZoneConfig(plugin, new File(plugin.getDataFolder().getAbsoluteFile()+File.separator+"arenas"+File.separator+s+".yml"));
            
            config.set("name", arena.getName());
            config.set("settings.tnt-enabled", arena.isTnTEnabled());
            config.set("settings.block-break", arena.isBlockBreakEnabled());
            config.set("spawns.lobby", arena.getLobbyLocation().getBlockX()+";"+arena.getLobbyLocation().getBlockY()+";"+arena.getLobbyLocation().getBlockZ());
            config.set("spawns.spectator-box", arena.getSpectateLocation().getBlockX()+";"+arena.getSpectateLocation().getBlockY()+";"+arena.getSpectateLocation().getBlockZ());
            
            ArrayList<Location> spawnPoints = arena.getSpawnPoints();
            List<String> spawnPointsStr = new ArrayList<>();
            for(Location loc : spawnPoints)
            {
                String locParse = loc.getBlockX()+";"+loc.getBlockY()+";"+loc.getBlockZ();
                spawnPointsStr.add(locParse);
            }
            config.set("spawns.arena", spawnPointsStr);
            
            config.set("region.arena", arena.getArenaBox().getSaveFormat());
            config.set("region.lobby", arena.getLobbyBox().getSaveFormat());
            config.set("region.spectator-box", arena.getSpectateBox().getSaveFormat());
            config.save();
            
            enabledArenas.add(s);
        }
        
        plugin.getConfig().set("enabled-arenas", enabledArenas);
        plugin.saveConfig();
    }

    
    public void loadAllArenas()
    {
        if(!plugin.getConfig().contains("enabled-arenas"))
        {
            return;
        }
        
        for(String s : plugin.getConfig().getStringList("enabled-arenas"))
        {
            
        }
    }
}
