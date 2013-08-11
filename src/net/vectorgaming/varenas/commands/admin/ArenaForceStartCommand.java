
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaForceStartCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length != 1)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
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
            return true;
        }
        
        ArenaManager.getArena(args[0].toLowerCase()).start();
        
        return true;
    }

    @Override
    public String getName()
    {
        return "arena forcestart";
    }

    @Override
    public String getUsage()
    {
        return "Usage: /arena forcestart <arena>";
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }

    @Override
    public String[] getAliases()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands(){}

    @Override
    public String getPermission()
    {
        return "varenas.forcestart";
    }

}
