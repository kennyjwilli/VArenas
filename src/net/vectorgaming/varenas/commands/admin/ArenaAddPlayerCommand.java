
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.commands.VCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaAddPlayerCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length != 2)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        Player p = Bukkit.getPlayer(args[1]);
        if(p == null)
        {
            cs.sendMessage(ChatColor.RED+"Error: Player "+ChatColor.YELLOW+args[1]+ChatColor.RED+" is not online.");
            return true;
        }
        
        if(!ArenaManager.isArenaQueued(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" is not queued.");
            return true;
        }
        
        ArenaPlayerManager.addPlayerToArena(args[0], p);
        cs.sendMessage(ChatColor.GREEN+"Successfully added player "+ChatColor.YELLOW+p.getName()+ChatColor.GREEN+" to arena "+ChatColor.YELLOW+args[0]);
        return true;
    }

    @Override
    public String getName()
    {
        return "arena addplayer";
    }

    @Override
    public String getUsage()
    {
        return "Usage: /arena addplayer <arena> <player>";
    }

    @Override
    public boolean isPlayerOnlyCommand()
    {
        return false;
    }

    @Override
    public String[] getAliases()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands(){}

    @Override
    public String getPermission()
    {
        return "varenas.addplayer";
    }

}
