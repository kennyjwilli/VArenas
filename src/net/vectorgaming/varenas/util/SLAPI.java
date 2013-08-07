
package net.vectorgaming.varenas.util;

import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.VArenas;
import net.vectorgaming.varenas.framework.ArenaDirectory;
import net.vectorgaming.varenas.framework.ArenaSettings;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
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
    /**
     * Saves all the arenas to the config files
     */
    public void saveAllArenas()
    {
        List<String> enabledArenas = new ArrayList<>();
        
        for(String s : ArenaManager.getMaps())
        {
            //Arena arena = ArenaManager.getArena(s);
            ArenaSettings settings = ArenaManager.getArenaSettings(s);
            ZoneConfig settingsConfig = new ZoneConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS_DIR+File.separator+s+".yml"));
            ArenaConfig frameworkConfig = ArenaManager.getArenaConfig(s.toLowerCase());
            
            settingsConfig.set("name", s.toLowerCase());
            /*
             * Arena info 
             */
            settingsConfig.set("info.authors", settings.getAuthors());
            settingsConfig.set("info.objective", settings.getObjective());
            /*
             * Arena settings
             */
            settingsConfig.set("settings.type", settings.getType());
            settingsConfig.set("settings.max-players", settings.getMaxPlayers());
            settingsConfig.set("settings.tnt-enabled", settings.isTNTEnabled());
            settingsConfig.set("settings.block-break", settings.isBlockBreakEnabled());
            /*
             * Lobby Settings
             */
            settingsConfig.set("settings.lobby.time", settings.getLobbyDuration());
            settingsConfig.set("settings.lobby.message-interval", settings.getLobbyMessageInterval());
            /*
             * Spawns
             */
            
            frameworkConfig.set("spawns.lobby", SLAPI.saveLocation(arena.getLobby().getSpawn()));
            config.set("spawns.spectator-box", SLAPI.saveLocation(arena.getSpectatorBox().getSpawn()));
            
            HashMap<String,Location> spawnPoints = arena.getSpawnPointMap();
            List<String> spawnPointsStr = new ArrayList<>();
            for(Map.Entry kv : spawnPoints.entrySet())
            {
                Location loc = (Location) kv.getValue();
                config.set("spawns.arena."+kv.getKey(), SLAPI.saveLocation(loc));
            }
            
            /*
             * Arena Regions
             */
            
            config.set("region.arena", arena.getArenaBox().toSaveString());
            //config.set("region.arena", arena.getArenaBox().getSaveFormat());
            //config.set("region.lobby", arena.getLobbyBox().getSaveFormat());
            //config.set("region.spectator-box", arena.getSpectateBox().getSaveFormat());
            config.save();
            
            enabledArenas.add(s);
        }
        
        /*
         * Save enabled arenas
         */
        plugin.getConfig().set("enabled-arenas", enabledArenas);
        plugin.saveConfig();
    }

    
    /**
     * Loads all arenas from their config files
     */
    public void loadAllArenas()
    {
        if(!plugin.getConfig().contains("enabled-arenas"))
        {
            return;
        }
        
        for(String s : plugin.getConfig().getStringList("enabled-arenas"))
        {
            
            ZoneConfig config = new ZoneConfig(plugin, new File(ArenaDirectory.SETTINGS_DIR.toString()));
            String type = config.getString("settings.type");
            
            ArenaManager.createMap(s);
            
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
            arena.setMaxPlayers(config.getInt("settings.max-players"));
            /*
             * Loads arena info
             */
            arena.setAuthors(config.getString("info.authors"));
            arena.setObjective(config.getString("info.objective"));
            try {
                /*
                 * Loads regions
                 */
                arena.setArenaBox(PolygonTriggerBox.getPolygonTriggerBox(config.getString("region.arena")));
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            
            //arena.setArenaBox(VRegion.loadSaveFormat(config.getStringList("region.arena")));
            
            /*
             * Ready loaded arenas
             */
            //ArenaManager.readyArena(s);
        }
    }
    
    public static String saveLocation(Location location)
    {
        return location.getWorld().getName()+";"+location.getBlockX()+";"+location.getBlockY()+";"+location.getBlockZ();
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
