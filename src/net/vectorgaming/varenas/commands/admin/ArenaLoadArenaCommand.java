
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.util.SLAPI;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaLoadArenaCommand extends SubCommand
{
    public ArenaLoadArenaCommand()
    {
        super("load", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        if(ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" is already loaded!");
            return;
        }
        
        if(!SLAPI.loadArena(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Could not load map. Files for map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" are either missing or do not exist.");
            return;
        }
        cs.sendMessage(ChatColor.GREEN+"Successfully loaded map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.GREEN+" from the disk!");
    }

    @Override
    public String getUsage() {return "/arena load <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

    @Override
    public String getPermission(){return "varenas.loadarena";}

    @Override
    public String getDescription()
    {
        return "Loads a map from a world";
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
