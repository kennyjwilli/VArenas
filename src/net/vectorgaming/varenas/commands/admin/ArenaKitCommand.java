
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaKitCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        Player  p = (Player) cs;
        boolean clear = true;
        
        if(args.length < 1 || args.length > 2)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(args.length == 2)
        {
            try
            {
                clear = Boolean.parseBoolean(args[1]);
            }catch(Exception e)
            {
                cs.sendMessage(ChatColor.RED+"Error: Could not parse value for clear inventory");
                return true;
            }
        }
        
        if(!KitManager.kitExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Kit "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" does not exist!");
            return true;
        }
        
        KitManager.getKit(args[0].toLowerCase()).giveKit(p, clear);
        cs.sendMessage(ChatColor.GREEN+"Gave you kit "+ChatColor.YELLOW+args[0].toLowerCase());
        return true;
    }

    @Override
    public String getName() {return "arena kit";}

    @Override
    public String getUsage() {return "Usage: /arena kit <name> [clearInventory]";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

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
    public String getPermission() {return "varenas.kit";}

}
