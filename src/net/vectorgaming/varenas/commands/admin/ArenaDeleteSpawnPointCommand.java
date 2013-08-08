
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.ArenaFramework;
import net.vectorgaming.varenas.util.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaDeleteSpawnPointCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments) 
    {
        Player p = (Player) cs;
        
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length != 2)
        {
            cs.sendMessage(getUsage());
            return true;
        }
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        ArenaFramework framework = ArenaManager.getAreanFramework(args[0]);
        
        if(!framework.deleteSpawn(args[1]))
        {
            boolean first = false;
            String output = "";
            int i = 1;
            
            for(String s : framework.getSpawnNames())
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
                    output += ChatColor.YELLOW+s;
                }
                
                i++;
            }
            
            cs.sendMessage(ChatColor.RED+"Error: Spawn point "+args[1]+" does not exist in map "+args[0].toLowerCase());
            cs.sendMessage(Msg.getPluginColor()+args[0].toLowerCase()+" Spawn Points: "+output);
            return true;
        }else
        {
            cs.sendMessage(ChatColor.RED+"Deleted spawn point "+ChatColor.YELLOW+args[1]+ChatColor.RED+" from map "+ChatColor.YELLOW+args[0]+ChatColor.RED+"!");
        }
        
        return true;
    }

    @Override
    public String getName() {return "arena deletespawn";}

    @Override
    public String getUsage() {return "Usage: /arena deletespawn <map> <spawn>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands() {}

    @Override
    public String getPermission() {return "varena.deletespawn";}

}
