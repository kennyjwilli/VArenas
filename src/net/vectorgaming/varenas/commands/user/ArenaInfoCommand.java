
package net.vectorgaming.varenas.commands.user;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.ArenaSettings;
import org.apache.commons.lang.WordUtils;
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
        
        ArenaSettings settings = ArenaManager.getArenaSettings(args[0]);
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        cs.sendMessage(ChatColor.BLUE+"========== "+ChatColor.GOLD+settings.getName()+" Info"+ChatColor.BLUE+" ==========");
        cs.sendMessage("Author(s): "+settings.getAuthors());
        cs.sendMessage("Map Type: "+WordUtils.capitalizeFully(settings.getType().replaceAll("_", " ")));
        cs.sendMessage("Objective: "+settings.getObjective());
        cs.sendMessage("Max Players: "+settings.getMaxPlayers());
        cs.sendMessage("TNT Enabled: "+settings.isTNTEnabled());
        cs.sendMessage("Block Break: "+settings.isBlockBreakEnabled());
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
