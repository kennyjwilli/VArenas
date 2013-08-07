package net.vectorgaming.varenas;

import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import net.vectorgaming.varenas.commands.CommandManager;
import net.vectorgaming.varenas.commands.user.ArenaCommand;
import net.vectorgaming.varenas.framework.enums.ArenaType;
import net.vectorgaming.varenas.framework.PVPArena;
import net.vectorgaming.varenas.framework.user.ArenaPlayer;
import net.vectorgaming.varenas.framework.user.ArenaPlayerManager;
import net.vectorgaming.varenas.listeners.PlayerDeathListener;
import net.vectorgaming.varenas.util.SLAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Kenny
 */
public class VArenas extends JavaPlugin
{
    private CommandManager cm = new CommandManager();
    private PlayerDeathListener dl = new PlayerDeathListener();
    private SLAPI slapi = new SLAPI(this);
    private ArenaManager am = new ArenaManager();
    private ArenaPlayerManager<ArenaPlayer> playerManager;
    
    @Override
    public void onEnable()
    {
        setupCommands();
        setupEvents();
        registerArenaTypes();
        slapi.loadAllArenas();
        ZoneConfig usersConfig = new ZoneConfig(this,new File(this.getDataFolder().getAbsoluteFile()+File.separator+"arena-players.yml"));
        playerManager = new ArenaPlayerManager(this,usersConfig);
    }
    
    @Override
    public void onDisable()
    {
        slapi.saveAllArenas();
        //Need to delete all arena maps 
        
        playerManager.getUsersConfig().save();
    }
    
    private void setupCommands()
    {
        this.getCommand("arena").setExecutor(cm);
        CommandManager.registerCommand("arena", ArenaCommand.class);
    }
    
    private void setupEvents()
    {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(dl, this);
    }
    
    private void registerArenaTypes()
    {
        ArenaRegister.registerArenaType(ArenaType.PVP_ARENA.toString(), PVPArena.class);
    }
}
