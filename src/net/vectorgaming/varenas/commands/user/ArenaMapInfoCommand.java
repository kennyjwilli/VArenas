
package net.vectorgaming.varenas.commands.user;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.ArenaSettings;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaMapInfoCommand extends SubCommand
{
    public ArenaMapInfoCommand()
    {
        super("map", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        Player p = (Player) cs;
        
        ArenaSettings settings = null;
        
        if(args.length == 0)
        {
            if(ArenaPlayerManager.isPlayerInArena(p))
            {
                settings = ArenaPlayerManager.getArenaFromPlayer(p).getSettings();
            }else
            {
                cs.sendMessage(ChatColor.RED+getUsage());
                return;
            }
        }
        
        if(args.length == 1)
        {
            if(!ArenaManager.mapExists(args[0].toLowerCase()))
            {
                cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
                return;
            }
            settings = ArenaManager.getArenaSettings(args[0].toLowerCase());
        }
        
        
        String solidLine = ChatColor.RED+""+ChatColor.STRIKETHROUGH+StringUtils.repeat(" ", 30);
        String color = ChatColor.DARK_PURPLE+""+ChatColor.BOLD;
        
        cs.sendMessage(solidLine+ChatColor.AQUA+" "+WordUtils.capitalizeFully(settings.getName())+" "+ChatColor.GRAY+settings.getMapVersion()+" "+solidLine);
        cs.sendMessage(color+"Objective: "+ChatColor.RED+settings.getObjective());
        cs.sendMessage(color+"Authors: "+ChatColor.RED+settings.getAuthors().replaceAll(",", ChatColor.WHITE+","));
        if(!settings.getRules().isEmpty())
        {
            int i = 1;
            for(String s : settings.getRules())
            {
                cs.sendMessage(i+") "+ChatColor.GOLD+s);
                i++;
            }
        }
        cs.sendMessage(color+"Max Players: "+ChatColor.RED+settings.getMaxPlayers());
    }

    @Override
    public String getUsage() {return "/arena info <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.mapinfo";}

    @Override
    public String getDescription()
    {
        return "Gets some info about an arena";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 0;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 1;
    }

}