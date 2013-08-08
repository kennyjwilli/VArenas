
package net.vectorgaming.varenas.util;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.VArenas;
import net.vectorgaming.varenas.framework.enums.ArenaDirectory;
import net.vectorgaming.varenas.framework.ArenaFramework;
import net.vectorgaming.varenas.framework.ArenaSettings;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import net.vectorgaming.varenas.framework.config.SettingsConfig;
import net.vectorgaming.varenas.framework.enums.ArenaYMLPath;
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
            ArenaFramework framework = ArenaManager.getAreanFramework(s);
            SettingsConfig settingsConfig = new SettingsConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS_DIR+File.separator+s+".yml"));
            ArenaConfig frameworkConfig = ArenaManager.getArenaConfig(s.toLowerCase());
            
            settingsConfig.set("name", s.toLowerCase());
            /*
             * Arena info 
             */
            settingsConfig.setAuthors(settings.getAuthors());
            settingsConfig.setObjective(settings.getObjective());
            
            /*
             * Arena settings
             */
            settingsConfig.setType(settings.getType());
            settingsConfig.setMaxPlayers(settings.getMaxPlayers());
            settingsConfig.setTntUse(settings.isTNTEnabled());
            settingsConfig.setBlockBreak(settings.isBlockBreakEnabled());
            
            /*
             * Lobby Settings
             */
            settingsConfig.setLobbyDuration(settings.getLobbyDuration());
            settingsConfig.setLobbyMessageInterval(settings.getLobbyMessageInterval());
            
            /*
             * Spawns (Old way of saving spawns)
             */
            
//            frameworkConfig.setLobbySpawn(framework.getLobbySpawn());
//            frameworkConfig.setSpectatorBoxSpawn(framework.getSpectatorBoxSpawn());
//            
//            HashMap<String,Point3D> spawnPoints = framework.getSpawnsMap();
//            
//            for(Map.Entry kv : spawnPoints.entrySet())
//            {
//                frameworkConfig.addArenaSpawn((String)kv.getKey(), (Point3D) kv.getValue());
//            }
            
            /*
             * Save all locations
             */
            for(String str : framework.getLocationMap().keySet())
            {
                frameworkConfig.addLocation(str, framework.getLocationMap().get(str));
            }
            
            /*
             * Arena Regions
             */
            for(String str : framework.getTriggerBoxMap().keySet())
            {
                frameworkConfig.set(str, framework.getTriggerBoxMap().get(str).toSaveString());
            }
            
            /*
             * Saves all edited files
             */
            settingsConfig.save();
            frameworkConfig.save();
            
            enabledArenas.add(s.toLowerCase());
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
            SettingsConfig settingsConfig = new SettingsConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS_DIR.toString()));
            ArenaConfig frameworkConfig = new ArenaConfig(plugin, new File(ArenaDirectory.ARENA_FRAMEWORK_DIR.toString()));
            ArenaManager.createMap(s);
            ArenaSettings settings = ArenaManager.getArenaSettings(s);
            ArenaFramework framework = ArenaManager.getAreanFramework(s);
            
            /*
             * Loads spawns
             */
            for(String str : frameworkConfig.getConfigurationSection(""+ArenaYMLPath.ARENA_SPAWNS).getKeys(false))
            {
                framework.addArenaSpawn(str, Point3D.toPoint3D(frameworkConfig.getString(""+ArenaYMLPath.ARENA_SPAWNS+"."+str)));
            }
            framework.setLobbyLocation(frameworkConfig.getLobbySpawn());
            framework.setSpectatorBoxSpawn(frameworkConfig.getSpectatorBoxSpawn());
            
            /*
             * Loads regions
             */
            try
            {
                framework.setArenaTriggerBox(frameworkConfig.getArenaBox());
            } catch (Exception ex)
            {
                Logger.getLogger(SLAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*
             * Loads arena info
             */
            settings.setAuthors(settingsConfig.getAuthors());
            settings.setObjective(settingsConfig.getObjective());
            
            /*
             * Loads arena settings
             */
            settings.setObjective(settingsConfig.getObjective());
            settings.setAuthors(settingsConfig.getAuthors());
            settings.setType(settingsConfig.getType());
            settings.setBlockBreak(settingsConfig.isBlockBreakEnabled());
            settings.setTNTUse(settingsConfig.isTnTEnabled());
            settings.setMaxPlayers(settingsConfig.getMaxPlayers());
            
            /*
             * Loads lobby settings
             */
            settings.setLobbyDuration(settingsConfig.getLobbyDuration());
            settings.setLobbyMessageInterval((ArrayList<String>)settingsConfig.getLobbyMessageInterval());
            
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
