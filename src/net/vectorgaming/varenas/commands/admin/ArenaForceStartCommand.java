
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
        
        if(args.length == 1)
        {
            if(!ArenaManager.arenaExists(args[0]))
            {
                cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
                return true;
            }
            
            Arena arena = ArenaManager.getArena(args[0]);
            if(!ArenaManager.isArenaReady(arena))
            {
                cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+arena.getName()+ChatColor.RED+" is not ready to be used.");
                return true;
            }
            
            arena.start();
        }
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
