
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaCreateMapCommand extends SubCommand
{
    public ArenaCreateMapCommand()
    {
        super("createmap", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        if(ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" already exists!");
            return;
        }
        
        ArenaManager.createMap(args[0].toLowerCase());
        cs.sendMessage(ChatColor.GREEN+"Successfully created map "+ChatColor.YELLOW+args[0].toLowerCase());
    }

    @Override
    public String getUsage() {return "Usage: /arena createmap <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission(){return "varenas.createmap";}

    @Override
    public String getDescription()
    {
        return "Creates a map for an arena";
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
