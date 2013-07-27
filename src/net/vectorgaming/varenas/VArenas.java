package net.vectorgaming.varenas;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.vectorgaming.varenas.commands.CommandManager;
import net.vectorgaming.varenas.commands.user.ArenaCommand;
import net.vectorgaming.varenas.framework.ArenaType;
import net.vectorgaming.varenas.framework.PVPArena;
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
    private ArenaManager am = new ArenaManager(this);
    
    @Override
    public void onEnable()
    {
        setupCommands();
        setupEvents();
        registerArenaTypes();
        slapi.loadAllArenas();
    }
    
    @Override
    public void onDisable()
    {
        slapi.saveAllArenas();
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
        ArenaRegistration.registerArenaType(ArenaType.PVP_ARENA.toString(), PVPArena.class);
    }
}
