package net.vectorgaming.varenas;

import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneTools;
import info.jeppes.ZoneWorld.ZoneWorld;
import java.io.File;
import net.vectorgaming.varenas.commands.CommandManager;
import net.vectorgaming.varenas.commands.user.ArenaCommand;
import net.vectorgaming.varenas.framework.enums.ArenaType;
import net.vectorgaming.varenas.framework.PVPArena;
import net.vectorgaming.varenas.framework.enums.ArenaDirectory;
import net.vectorgaming.varenas.framework.pvparena.PVPArenaCreator;
import net.vectorgaming.varenas.listeners.PlayerBlockBreakListener;
import net.vectorgaming.varenas.listeners.PlayerDamageListener;
import net.vectorgaming.varenas.listeners.PlayerRespawnListener;
import net.vectorgaming.varenas.util.SLAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Kenny
 */
public class VArenas extends JavaPlugin
{
    private CommandManager cm = new CommandManager();
    private PlayerDamageListener edl = new PlayerDamageListener();
    private PlayerRespawnListener prl = new PlayerRespawnListener();
    private PlayerBlockBreakListener pbl = new PlayerBlockBreakListener();
    private SLAPI slapi = new SLAPI(this);
    private ArenaManager am = new ArenaManager();
    //private ArenaPlayerManager<ArenaPlayer> playerManager;
    private ArenaAPI arenaAPI;
    
    @Override
    public void onEnable()
    {
        arenaAPI = new ArenaAPI(this);
        setupCommands();
        setupEvents();
        registerArenaTypes();
        slapi.loadAllArenas();
        ZoneConfig usersConfig = new ZoneConfig(this,new File("plugins/VArenas/arena-players.yml"));
        //playerManager = new ArenaPlayerManager(this,usersConfig);
    }
    
    @Override
    public void onDisable()
    {
        slapi.saveAllArenas();
        unloadAndDeleteArenas();
        //Need to delete all arena maps 
        
        //playerManager.getUsersConfig().save();
    }
    
    private void unloadAndDeleteArenas()
    {
        for(Player p : ArenaPlayerManager.getAllArenaPlayers())
        {
            p.teleport(Bukkit.getWorld("spawn").getSpawnLocation());
        }
        for(ZoneWorld world : ArenaManager.getArenaWorlds())
        {
            world.unloadWorld();
            world.deleteWorld();
        }
        for(File f : new File(ArenaDirectory.ARENAS_DIR).listFiles())
        {
            ZoneTools.deleteDirectory(f);
        }
    }
    
    private void setupCommands()
    {
        this.getCommand("arena").setExecutor(cm);
        CommandManager.registerCommand("arena", ArenaCommand.class);
    }
    
    private void setupEvents()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(edl, this);
        pm.registerEvents(prl, this);
        pm.registerEvents(pbl, this);
    }
    
    private void registerArenaTypes()
    {
        ArenaAPI.registerArenaCreator("PVP_ARENA", new PVPArenaCreator());    
    }
}
