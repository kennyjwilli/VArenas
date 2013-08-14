
package net.vectorgaming.varenas.commands.user;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.ArenaSettings;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaMapInfoCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
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
                return true;
            }
        }
        
        if(args.length == 1)
        {
            if(!ArenaManager.mapExists(args[0].toLowerCase()))
            {
                cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
                return true;
            }
            settings = ArenaManager.getArenaSettings(args[0].toLowerCase());
        }
        
        
        String solidLine = ChatColor.RED+""+ChatColor.STRIKETHROUGH+StringUtils.repeat(" ", 15);
        String color = ChatColor.BOLD+""+ChatColor.DARK_PURPLE;
        
        cs.sendMessage(solidLine+ChatColor.AQUA+WordUtils.capitalizeFully(settings.getName())+" "+ChatColor.GRAY+settings.getMapVersion()+solidLine);
        cs.sendMessage(color+"Objective: "+settings.getObjective());
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
        cs.sendMessage(color+"Max Players: "+settings.getMaxPlayers());
        return true;
    }

    @Override
    public String getName() {return "arena info";}

    @Override
    public String getUsage() {return "Usage: /arena info <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPermission() {return "varenas.mapinfo";}

}
