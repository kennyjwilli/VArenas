
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaSetLobbySpawnCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments) 
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        Player p = (Player) cs;
        
        if(args.length != 1)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
        }
                
        ArenaManager.getAreanFramework(args[0].toLowerCase()).setLobbySpawn(p.getLocation());
        cs.sendMessage(ChatColor.GREEN+"Successfully set lobby location for map "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+".");
        //arena.checkArenaSetup(p);
        return true;
    }

    @Override
    public String getName() {return "arena setlobby";}

    @Override
    public String getUsage() {return "Usage: /arena setlobby <arena>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands() {}

    @Override
    public String getPermission() {return "varenas.setlobby";}

}
