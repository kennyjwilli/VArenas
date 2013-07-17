package net.vectorgaming.varenas;

import net.vectorgaming.varenas.commands.CommandManager;
import net.vectorgaming.varenas.commands.user.ArenaCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Kenny
 */
public class VArenas extends JavaPlugin
{
    private CommandManager cm = new CommandManager();
    
    public void onEnable()
    {
        setupCommands();
    }
    
    public void onDisable(){}
    
    private void setupCommands()
    {
        this.getCommand("arena").setExecutor(cm);
        CommandManager.registerCommand("arena", ArenaCommand.class);
    }
}
