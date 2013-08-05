
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.Arena;
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
        
        if(!ArenaManager.mapExists(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        Arena arena = ArenaManager.getArena(args[0]);
        
        if(!arena.deleteSpawnPoint(args[1]))
        {
            boolean first = false;
            String output = "";
            int i = 1;
            
            for(String s : arena.getSpawnPointsNames())
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
            
            cs.sendMessage(ChatColor.RED+"Error: Spawn point "+args[1]+" does not exist in arena "+arena.getName());
            cs.sendMessage(Msg.getPluginColor()+arena.getName()+" Spawn Points: "+output);
            return true;
        }else
        {
            cs.sendMessage(ChatColor.RED+"Deleted spawn point "+ChatColor.YELLOW+args[1]+ChatColor.RED+" from arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+"!");
        }
        
        return true;
    }

    @Override
    public String getName() {return "arena deletespawn";}

    @Override
    public String getUsage() {return "Usage: /arena deletespawn <arena> <spawn>";}

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
