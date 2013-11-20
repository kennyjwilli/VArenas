
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
public class ArenaSetSpectateSpawnCommand extends SubCommand
{
    public ArenaSetSpectateSpawnCommand()
    {
        super("setregion", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args) 
    {
        Player p = (Player) cs;
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return;
        }
                
        ArenaManager.getAreanFramework(args[0].toLowerCase()).setSpectatorBoxSpawn(p.getLocation());
        cs.sendMessage(ChatColor.GREEN+"Successfully set spectator box location for map "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+".");
        //arena.checkArenaSetup(p);
    }

    @Override
    public String getUsage() {return "/arena setspectatorbox <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.setspectatorbox";}

    @Override
    public String getDescription()
    {
        return "Sets the spectator box spawn for a map";
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
