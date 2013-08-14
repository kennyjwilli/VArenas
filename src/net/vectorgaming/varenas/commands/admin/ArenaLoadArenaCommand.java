
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.util.SLAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaLoadArenaCommand extends VCommand
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
        
        if(ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" is already loaded!");
            return true;
        }
        
        if(!SLAPI.loadArena(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Could not load map. Files for map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" are either missing or do not exist.");
            return true;
        }
        cs.sendMessage(ChatColor.GREEN+"Successfully loaded map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.GREEN+" from the disk!");
        return true;
    }

    @Override
    public String getName() {return "arena load";}

    @Override
    public String getUsage() {return "Usage: /arena load <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

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
    public String getPermission(){return "varenas.loadarena";}
}
