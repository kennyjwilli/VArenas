
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaSetLobbySpawnCommand extends SubCommand
{
    public ArenaSetLobbySpawnCommand()
    {
        super("setlobby", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args) 
    {
        Player p = (Player) cs;
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
        }
                
        ArenaManager.getAreanFramework(args[0].toLowerCase()).setLobbySpawn(p.getLocation());
        cs.sendMessage(ChatColor.GREEN+"Successfully set lobby location for map "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+".");
        //arena.checkArenaSetup(p);
    }

    @Override
    public String getUsage() {return "Usage: /arena setlobby <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.setlobby";}

    @Override
    public String getDescription()
    {
        return "Sets the lobby spawn for a map";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 1;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 1;
    }

}
