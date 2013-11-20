
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.util.SLAPI;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaSetPostGameSpawnCommand extends SubCommand
{
    public ArenaSetPostGameSpawnCommand()
    {
        super("reload", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return;
        }
        
        ArenaManager.getArenaSettings(args[0].toLowerCase()).setPostGameSpawn(SLAPI.saveLocation(((Player) cs).getLocation()));
        cs.sendMessage(ChatColor.GREEN+"Successfully set post game spawn location for map "+args[0].toLowerCase());
    }

    @Override
    public String getName() {return "arena setpostgamespawn";}

    @Override
    public String getUsage() {return "/arena setpostgamespawn <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.setpostgamespawn";}

    @Override
    public String getDescription()
    {
        return "Sets the post game spawn for a map";
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
