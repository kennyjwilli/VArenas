
package net.vectorgaming.varenas.commands.user;

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
public class ArenaInfoCommand extends VCommand
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
        
        cs.sendMessage(ChatColor.BLUE+"========== "+ChatColor.GOLD+arena.getName()+" Info"+ChatColor.BLUE+" ==========");
        cs.sendMessage("Author(s): "+arena.getAuthors());
        cs.sendMessage("Map Type: "+arena.getT);
        cs.sendMessage("Objective: "+arena.getObjective());
        cs.sendMessage("Max Players: "+arena.getMaxPlayers());
        cs.sendMessage("TNT Enabled: "+arena.isTNTEnabled());
        cs.sendMessage("Block Break: "+arena.isBlockBreakEnabled());
        return true;
    }

    @Override
    public String getName()
    {
        return "arena info";
    }

    @Override
    public String getUsage()
    {
        return "Usage: /arena info <arena>";
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
    public void setupSubCommands()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPermission()
    {
        return "varenas.info";
    }

}
