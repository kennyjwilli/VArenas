
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
public class ArenaAddSpawnPointCommand extends SubCommand
{
    public ArenaAddSpawnPointCommand()
    {
        super("addspawn", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args) 
    {
        Player p = (Player) cs;
        
        if(!ArenaManager.mapExists(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return;
        }
        
        ArenaManager.getAreanFramework(args[0].toLowerCase()).addArenaSpawn(args[1].toLowerCase(), p.getLocation());
        
        cs.sendMessage(ChatColor.GREEN+"Added spawn point "+ChatColor.YELLOW+args[1]+ChatColor.GREEN+" to map "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+"!");
    }

    @Override
    public String getUsage() {return "/arena addspawn <map> <spawn>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varena.addspawn";}

    @Override
    public String getDescription()
    {
        return "Adds a spawn to a map";
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
