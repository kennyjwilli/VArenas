package net.vectorgaming.varenas;

import info.jeppes.ZoneCore.ZoneConfig;
import info.jeppes.ZoneCore.ZoneTools;
import info.jeppes.ZoneWorld.ZoneWorld;
import java.io.File;
import net.vectorgaming.varenas.chat.ArenaChannelCreator;
import net.vectorgaming.varenas.commands.user.ArenaCommand;
import net.vectorgaming.varenas.framework.enums.ArenaDirectory;
import net.vectorgaming.varenas.framework.pvparena.PVPArenaCreator;
import net.vectorgaming.varenas.listeners.*;
import net.vectorgaming.varenas.util.SLAPI;
import net.vectorgaming.vchat.VChatAPI;
import net.vectorgaming.vcore.framework.VertexAPI;
import net.vectorgaming.vcore.framework.VertexPlugin;
import net.vectorgaming.vcore.framework.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 *
 * @author Kenny
 */
public class VArenas extends VertexPlugin
{
    private final SLAPI slapi = new SLAPI(this);
    //private ArenaPlayerManager<ArenaPlayer> playerManager;
    private ArenaAPI arenaAPI;
    
    @Override
    public void onEnable()
    {
        this.saveDefaultConfig();
        arenaAPI = new ArenaAPI(this);
        if(!arenaAPI.canPluginStart()) return;
        setupCommands();
        setupEvents();
        registerArenaTypes();
        slapi.loadAllArenas();
        ZoneConfig usersConfig = new ZoneConfig(this,new File("plugins/VArenas/arena-players.yml"));
        VChatAPI.registerChannelType("ARENA_CHANNEL", new ArenaChannelCreator());
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
        for(File f : new File(ArenaDirectory.ARENAS).listFiles())
        {
            ZoneTools.deleteDirectory(f);
        }
    }
    
    @Override
    public void setupCommands()
    {
        CommandManager.registerCommand(new ArenaCommand());
    }
    
    private void setupEvents()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerDamageListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerBlockBreakListener(), this);
        pm.registerEvents(new CommandPreprocess(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
    }
    
    private void registerArenaTypes()
    {
        ArenaAPI.registerArenaCreator("PVP_ARENA", new PVPArenaCreator());    
    }

    @Override
    public Plugin getPlugin()
    {
        return this;
    }

    @Override
    public VertexAPI getAPI()
    {
        return arenaAPI;
    }
}
