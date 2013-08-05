
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaAddLocation extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length != 2)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(!ArenaManager.arenaExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        ArenaConfig framework = ArenaManager.getArenaConfig(args[0].toLowerCase());
        framework.addLocation(args[1].toLowerCase(), ((Player) cs).getLocation());
        cs.sendMessage(ChatColor.GREEN+"Successfully added new location for "+ChatColor.YELLOW+args[1].toLowerCase()+ChatColor.GREEN+" in map "+ChatColor.YELLOW+args[0].toLowerCase());
        return true;
    }

    @Override
    public String getName() {return "arena addlocation";}

    @Override
    public String getUsage(){return "Usage: /arena addlocation <map> <locationName>";}

    @Override
    public boolean isPlayerOnlyCommand(){return true;}

    @Override
    public String[] getAliases()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands(){}

    @Override
    public String getPermission(){return "varenas.addlocation";}

}