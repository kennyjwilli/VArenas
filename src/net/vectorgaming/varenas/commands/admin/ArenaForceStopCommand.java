
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaForceStopCommand extends SubCommand
{
    public ArenaForceStopCommand()
    {
        super("forcestop", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {        
        
        if(!ArenaManager.isArenaRunning(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" is not running or queued.");
            return;
        }
        
        Arena arena = ArenaManager.getArena(args[0]);
        
        arena.forceStop();
        
        cs.sendMessage(ChatColor.GREEN+"Successfully force stopped arena "+ChatColor.YELLOW+arena.getName()+ChatColor.GREEN+".");
    }

    @Override
    public String getUsage()
    {
        return "/arena forcestop <arena>";
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }

    @Override
    public String getPermission()
    {
        return "varenas.forcestop";
    }

    @Override
    public String getDescription()
    {
        return "Focibly stops an arena from running";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 1;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 1;
    }

}
