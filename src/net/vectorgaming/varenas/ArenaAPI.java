package net.vectorgaming.varenas;

import net.vectorgaming.varenas.framework.ArenaCreator;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

/**
 *
 * @author jeppe
 */
public class ArenaAPI 
{
    private static VArenas plugin;
    private static World hubWorld;
    private boolean canStart = true;
    private static List<String> allowedCommands;
    private static HashMap<String, ArenaCreator> maps = new HashMap<>();
    
    public ArenaAPI(VArenas plugin)
    {
        ArenaAPI.plugin = plugin;
        hubWorld = Bukkit.getWorld(plugin.getConfig().getString("hub-world"));
        if(hubWorld == null)
        {
            canStart = false;
            Bukkit.getLogger().log(Level.SEVERE, "[VArenas] Hub world \""+plugin.getConfig().getString("hub-world")+"\" does not exist!");
            Bukkit.getLogger().log(Level.SEVERE, "[VArenas] Disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
        allowedCommands = plugin.getConfig().getStringList("allowed-commands");
    }
    
    /**
     * Checks to see if the plugin can enable
     * @return boolean value
     */
    public boolean canPluginStart(){return canStart;}

    /**
     * Gets the VArenas plugin
     * @return VArenas
     */
    public static VArenas getPlugin() 
    {
        return plugin;
    }
    
    /**
     * Gets the plugin data folder
     * @return File object
     */
    public static File getDataFolder() 
    {
        return plugin.getDataFolder();
    }
    
    /**
     * Registers an ArenaCreator for a map type
     * @param type String
     * @param arenaCreator ArenaCreator
     */
    public static void registerArenaCreator(String type, ArenaCreator arenaCreator)
    {
        maps.put(type, arenaCreator);
    }
    
    /**
     * Unregisters an ArenaCreator for a map type
     * @param type String
     */
    public static void unregisterArenaCreator(String type)
    {
        maps.remove(type);
    }
    
    /**
     * Gets the ArenaCreator for the specified type
     * @param type String
     * @return ArenaCreator
     */
    public static ArenaCreator getArenaCreator(String type)
    {
        return maps.get(type);
    }
    
    /**
     * Gets the hub world
     * @return Bukkit world
     */
    public static World getHubWorld()
    {
        return hubWorld;
    }
    
    /**
     * Gets a list of allowed commands
     * @return List of commands
     */
    public static List<String> getAllowedCommands()
    {
        return allowedCommands;
    }
    
    /**
     * Cancels the respawn screen for the given player and resets everything to normal
     * @param p Player to cancel the screen
     */
    public static void resetPlayerState(Player p)
    {
//        Packet205ClientCommand packet = new Packet205ClientCommand();
//        packet.a = 1;
//        ((CraftPlayer) p).getHandle().playerConnection.a(packet);
        p.setHealth(20D);
        p.setFoodLevel(20);
        for(PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());
        p.setFireTicks(0);
        p.setVelocity(new Vector());
    }

}
