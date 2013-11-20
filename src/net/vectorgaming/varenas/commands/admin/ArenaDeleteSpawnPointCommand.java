
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.ArenaFramework;
import net.vectorgaming.varenas.util.Msg;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaDeleteSpawnPointCommand extends SubCommand
{
    public ArenaDeleteSpawnPointCommand()
    {
        super("deletespawn", ArenaAPI.getPlugin());
    }
    @Override
    public void run(CommandSender cs, String[] args) 
    {
        Player p = (Player) cs;
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return;
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
        }else
        {
            cs.sendMessage(ChatColor.RED+"Deleted spawn point "+ChatColor.YELLOW+args[1]+ChatColor.RED+" from map "+ChatColor.YELLOW+args[0]+ChatColor.RED+"!");
        }
    }

    @Override
    public String getUsage() {return "Usage: /arena deletespawn <map> <spawn>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varena.deletespawn";}

    @Override
    public String getDescription()
    {
        return "Deletes a spawn point from a map";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 2;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 2;
    }

}
