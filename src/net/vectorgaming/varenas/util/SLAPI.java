
package net.vectorgaming.varenas.util;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    private static VArenas plugin;
    
    public SLAPI(VArenas instance)
    {
        plugin = instance;
    }
    /**
     * Saves all the arenas to the config files
     */
    public void saveAllArenas()
    {
        for(String s : ArenaManager.getMaps())
            saveArena(s);
    }
    
    /**
     * Saves an arena to the config. 
     * 
     * @param map Name of the map to be saved
     */
    public static void saveArena(String map)
    {
        ArenaSettings settings = ArenaManager.getArenaSettings(map);
        ArenaFramework framework = ArenaManager.getAreanFramework(map);
        SettingsConfig settingsConfig = new SettingsConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS+File.separator+map+".yml"));
        ArenaConfig frameworkConfig = ArenaManager.getArenaConfig(map.toLowerCase());

        settingsConfig.set("name", map.toLowerCase());
        /*
         * Arena info 
         */
        settingsConfig.setAuthors(settings.getAuthors());
        settingsConfig.setObjective(settings.getObjective());

        /*
         * Arena settings
         */
        settingsConfig.setType(settings.getType());
        settingsConfig.setMapVersion(settings.getMapVersion());
        settingsConfig.setMaxPlayers(settings.getMaxPlayers());
        settingsConfig.setMinPlayers(settings.getMinPlayers());
        settingsConfig.setTNTUse(settings.isTNTEnabled());
        settingsConfig.setBlockBreak(settings.isBlockBreakEnabled());
        settingsConfig.setShowRespawnScreen(settings.isShowRespawnScreen());
        settingsConfig.setWinningKills(settings.getWinningKills());
        settingsConfig.setGameDuration(settings.getGameDuration());
        settingsConfig.setPostGameSpawn(settings.getPostGameSpawn());
        settingsConfig.setRules(settings.getRules());
        settingsConfig.setRespawnWithKit(settings.isRespawnWithKit());
        settingsConfig.setKitClearInventory(settings.isKitClearInventory());
        settingsConfig.setSpawnKitEnabler(settings.isSpawnKitEnabled());
        settingsConfig.setSpawnKitName(settings.getSpawnKitName());
        settingsConfig.setCustomKitsEnabled(settings.isCustomKitsEnabled());
        settingsConfig.setAllowedCustomKits(settings.getAllowedCustomKits());

        /*
         * Lobby Settings
         */
        settingsConfig.setLobbyDuration(settings.getLobbyDuration());
        settingsConfig.setLobbyMessageInterval(settings.getLobbyMessageInterval());

        /*
         * Save all locations
         */
        for(String str : framework.getLocationMap().keySet())
        {
            frameworkConfig.addLocation(str, framework.getLocationMap().get(str));
        }

        /*
         * Save all Arena Regions
         */
        for(String str : framework.getTriggerBoxMap().keySet())
        {
            frameworkConfig.set(str, framework.getTriggerBoxMap().get(str).toSaveString());
        }
        
        /*
         * Saves config files
         */
        settingsConfig.save();
        frameworkConfig.save();
        
        /*
         * Add to enabled arenas list and save in config.yml
         */
        List<String> enabledArenas;
        if(!plugin.getConfig().contains("enabled-arenas"))
        {
            enabledArenas = new ArrayList<>();
        }else
        {
            enabledArenas = plugin.getConfig().getStringList("enabled-arenas");
        }
        if(!enabledArenas.contains(map))
            enabledArenas.add(map);
        
        plugin.getConfig().set("enabled-arenas", enabledArenas);
        plugin.saveConfig();
    }

    
    /**
     * Loads all arenas from their config files
     */
    public void loadAllArenas()
    {
        if(!plugin.getConfig().contains("enabled-arenas"))
            return;
        
        for(String s : plugin.getConfig().getStringList("enabled-arenas"))
            loadArena(s);
    }
    
    /**
     * Loads an arena with all its settings from the configs
     * @param map Name of the map
     * @return boolean value if all the files exist to load the arena
     */
    public static boolean loadArena(String map)
    {
        File settingsFile = new File(ArenaDirectory.ARENA_SETTINGS+""+File.separator+map+".yml");
        File frameworkFile = new File(ArenaDirectory.ARENA_FRAMEWORK+""+File.separator+map+".yml");
        if(!settingsFile.exists() || !frameworkFile.exists())
            return false;
        SettingsConfig settingsConfig = new SettingsConfig(plugin, new File(ArenaDirectory.ARENA_SETTINGS+File.separator+map+".yml"));
        ArenaConfig frameworkConfig = new ArenaConfig(plugin, new File(ArenaDirectory.ARENA_FRAMEWORK+File.separator+map+".yml"));
        ArenaManager.createMap(map);
        ArenaSettings settings = ArenaManager.getArenaSettings(map);
        ArenaFramework framework = ArenaManager.getAreanFramework(map);

        /*
         * Loads spawns
         */
        for(String str : frameworkConfig.getConfigurationSection(ArenaYMLPath.ARENA_SPAWNS).getKeys(false))
            framework.addArenaSpawn(str, Point3D.toPoint3D(frameworkConfig.getString(ArenaYMLPath.ARENA_SPAWNS+"."+str)));
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
        settings.setTNTUse(settingsConfig.isTNTEnabled());
        settings.setMaxPlayers(settingsConfig.getMaxPlayers());
        settings.setMinPlayers(settingsConfig.getMinPlayers());
        settings.setShowRespawnScreen(settingsConfig.isShowRespawnScreen());
        settings.setWinningKills(settingsConfig.getWinningKills());
        settings.setGameDuration(settingsConfig.getGameDuration());
        settings.setPostGameSpawn(settingsConfig.getPostGameSpawn());
        settings.setRules(settingsConfig.getRules());
        settings.setMapVersion(settingsConfig.getMapVersion());
        settings.setSpawnKitEnabler(settingsConfig.isSpawnKitEnabled());
        settings.setSpawnKitName(settingsConfig.getSpawnKitName());
        settings.setKitClearInventory(settingsConfig.isKitClearInventory());
        settings.setAllowedCustomKits(settingsConfig.getAllowedCustomKits());
        settings.setCustomKitsEnabled(settingsConfig.isCustomKitsEnabled());
        settings.setRespawnWithKit(settingsConfig.isRespawnWithKit());

        /*
         * Loads lobby settings
         */
        settings.setLobbyDuration(settingsConfig.getLobbyDuration());
        settings.setLobbyMessageInterval((ArrayList<String>)settingsConfig.getLobbyMessageInterval());
        return true;
    }
    
    /**
     * Saves a Bukkit location
     * @param location Bukkit Location to be saved
     * @return Location in its save format
     */
    public static String saveLocation(Location location)
    {
        return location.getWorld().getName()+";"+location.getBlockX()+";"+location.getBlockY()+";"+location.getBlockZ();
    }
    
    /**
     * Loads a location from its save format
     * @param save The string the location is saved in
     * @return Bukkit Location
     */
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
