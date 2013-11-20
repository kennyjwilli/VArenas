
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaForceStartCommand extends SubCommand
{
    public ArenaForceStartCommand()
    {
        super("forcestart", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        
        if(!ArenaManager.isArenaQueued(args[0]))
        {
            String output = "";
            int i = 1;
            boolean first = false;
            for(String s : ArenaManager.getQueuedArenas())
            {
                if(first)
                {
                    output += ChatColor.WHITE+", ";
                }else
                {
                    first = true;
                }
                if(i % 2 == 0)
                {
                    output += ChatColor.GOLD+s;
                }else
                {
                    output += ChatColor.AQUA+s;
                }
                i++;
            }
            
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" is not currently queued.");
            cs.sendMessage(ChatColor.BLUE+"Currently queued arenas: "+output);
            return;
        }
        
        ArenaManager.getArena(args[0].toLowerCase()).start();
    }

    @Override
    public String getUsage()
    {
        return "/arena forcestart <arena>";
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }

    @Override
    public String getPermission()
    {
        return "varenas.forcestart";
    }

    @Override
    public String getDescription()
    {
        return "Force-starts a queued arena";
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
