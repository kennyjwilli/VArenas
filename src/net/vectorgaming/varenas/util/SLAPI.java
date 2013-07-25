
package net.vectorgaming.varenas.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.VArenas;
import net.vectorgaming.varenas.framework.VArena;
import net.vectorgaming.varenas.framework.PVPArena;
import net.vectorgaming.varenas.framework.VRegion;
import org.bukkit.Bukkit;
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
            VArena arena = ArenaManager.getArena(s);
            ZoneConfig config = new ZoneConfig(plugin, new File(plugin.getDataFolder().getAbsoluteFile()+File.separator+"arenas"+File.separator+s+".yml"));
            
            config.set("name", arena.getName());
            /*
             * Arena settings
             */
            config.set("settings.type", arena.getEventType());
            config.set("settings.tnt-enabled", arena.isTnTEnabled());
            config.set("settings.block-break", arena.isBlockBreakEnabled());
            /*
             * Lobby Settings
             */
            config.set("settings.lobby.time", arena.getLobby().getLobbyDuration());
            config.set("settings.lobby.message-interval", arena.getLobby().getInterval());
            /*
             * Spawns
             */
            config.set("spawns.lobby", SLAPI.saveLocation(arena.getLobby().getSpawn()));
            config.set("spawns.spectator-box", SLAPI.saveLocation(arena.getSpectatorBox().getSpawn()));
            
            HashMap<String,Location> spawnPoints = arena.getSpawnPointMap();
            List<String> spawnPointsStr = new ArrayList<>();
            for(Map.Entry kv : spawnPoints.entrySet())
            {
                Location loc = (Location) kv.getValue();
                config.set("spawns.arena."+kv.getKey(), SLAPI.saveLocation(loc));
            }
            
            //config.set("region.arena", arena.getArenaBox().getSaveFormat());
            //config.set("region.lobby", arena.getLobbyBox().getSaveFormat());
            //config.set("region.spectator-box", arena.getSpectateBox().getSaveFormat());
            config.save();
            
            enabledArenas.add(s);
        }
        
        plugin.getConfig().set("enabled-arenas", enabledArenas);
        plugin.saveConfig();
    }

    
    public void loadAllArenas() throws Exception
    {
        if(!plugin.getConfig().contains("enabled-arenas"))
        {
            return;
        }
        
        for(String s : plugin.getConfig().getStringList("enabled-arenas"))
        {
            VArena arena = new PVPArena(s);
            ZoneConfig config = new ZoneConfig(plugin, new File(plugin.getDataFolder().getAbsoluteFile()+File.separator+"arenas"+s+".yml"));
            
            /*
             * Loads spawns
             */
            arena.getLobby().setSpawn(getLocationFromSave(config.getString("spawns.lobby")));
            arena.getSpectatorBox().setSpawn(getLocationFromSave(config.getString("spawns.spectator-box")));
            for(String str : config.getConfigurationSection("spawns.arenas").getKeys(false))
                arena.addSpawnPoint(str, getLocationFromSave(config.getString("spawns.arena."+str)));
            /*
             * Loads arena settings
             */
            arena.setTnTUse(config.getBoolean("settings.tnt-enabled"));
            arena.setBlockBreak(config.getBoolean("settings.block-break"));
            
            /*
             * Loads lobby settings
             */
            
            arena.getLobby().setInterval((ArrayList<String>)config.getStringList("settings.lobby.messsage-interval"));
            arena.getLobby().setLobbyDuration(config.getInt("settings.lobby.time"));
            
            //arena.setArenaBox(VRegion.loadSaveFormat(config.getStringList("region.arena")));
        }
    }
    
    public static String saveLocation(Location location)
    {
        return location.getWorld().getName()+location.getBlockX()+";"+location.getBlockY()+";"+location.getBlockZ();
    }
    
    public static Location getLocationFromSave(String save)
    {
        String[] split = save.split(";");
        String world = split[0];
        if(Bukkit.getWorld(world) == null)
            return null;
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
