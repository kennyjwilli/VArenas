
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.VArena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaForceStopCommand extends VCommand
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
        
        VArena arena = ArenaManager.getArena(args[0]);
        
        if(!ArenaManager.arenaExists(arena))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        if(arena.isRunning())
        {
            arena.forceStop();
        }
        
        cs.sendMessage(ChatColor.GREEN+"Successfully force stopped arena "+ChatColor.YELLOW+arena.getName()+ChatColor.GREEN+".");
        
        return true;
    }

    @Override
    public String getName()
    {
        return "arena forcestop";
    }

    @Override
    public String getUsage()
    {
        return "Usage: /arena forcestop <arena>";
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
        return "varenas.forcestop";
    }

}
