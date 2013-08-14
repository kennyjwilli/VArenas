
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.util.SLAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaSetPostGameSpawnCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length != 1)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        ArenaManager.getArenaSettings(args[0].toLowerCase()).setPostGameSpawn(SLAPI.saveLocation(((Player) cs).getLocation()));
        cs.sendMessage(ChatColor.GREEN+"Successfully set post game spawn location for map "+args[0].toLowerCase());
        return true;
    }

    @Override
    public String getName() {return "arena setpostgamespawn";}

    @Override
    public String getUsage() {return "Usage: /arena setpostgamespawn <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands(){}

    @Override
    public String getPermission() {return "varenas.setpostgamespawn";}

}
