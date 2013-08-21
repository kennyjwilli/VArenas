
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.arenakits.KitManager;
import net.vectorgaming.arenakits.framework.Kit;
import net.vectorgaming.varenas.commands.VCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaAddKitCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        Player p = (Player) cs;
        
        if(args.length != 1)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(KitManager.kitExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Kit "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" already exists!");
            return true;
        }
        
        Kit kit = new Kit(args[0].toLowerCase());
        kit.setInventoryContents(p.getInventory().getContents());
        kit.setArmorContents(p.getInventory().getArmorContents());
        KitManager.addKit(args[0].toLowerCase(), kit);
        
        cs.sendMessage(ChatColor.GREEN+"Successfully added kit "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.GREEN+"!");
        return true;
    }

    @Override
    public String getName() {return "arena addkit";}

    @Override
    public String getUsage() {return "Usage: /arena addkit <name>";}

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
    public String getPermission() {return "varenas.addkit";}

}
