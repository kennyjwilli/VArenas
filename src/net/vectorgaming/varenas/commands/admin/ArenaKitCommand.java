
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.arenakits.KitManager;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaKitCommand extends SubCommand
{
    public ArenaKitCommand()
    {
        super("kit", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        Player  p = (Player) cs;
        boolean clear = true;
        
        if(args.length == 2)
        {
            try
            {
                clear = Boolean.parseBoolean(args[1]);
            }catch(Exception e)
            {
                cs.sendMessage(ChatColor.RED+"Error: Could not parse value for clear inventory");
                return;
            }
        }
        
        if(!KitManager.kitExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Kit "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" does not exist!");
            return;
        }
        
        KitManager.getKit(args[0].toLowerCase()).giveKit(p, clear);
        cs.sendMessage(ChatColor.GREEN+"Gave you kit "+ChatColor.YELLOW+args[0].toLowerCase());
    }

    @Override
    public String getUsage() {return "/arena kit <name> [clearInventory]";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.kit";}

    @Override
    public String getDescription()
    {
        return "Gives you an arena kit";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 1;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 2;
    }

}
