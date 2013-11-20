
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaAddPlayerCommand extends SubCommand
{
    public ArenaAddPlayerCommand()
    {
        super("addplayer", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {        
        Player p = Bukkit.getPlayer(args[1]);
        if(p == null)
        {
            cs.sendMessage(ChatColor.RED+"Error: Player "+ChatColor.YELLOW+args[1]+ChatColor.RED+" is not online.");
            return;
        }
        
        if(!ArenaManager.isArenaQueued(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" is not queued.");
            return;
        }
        
        ArenaPlayerManager.addPlayerToArena(args[0], p);
        cs.sendMessage(ChatColor.GREEN+"Successfully added player "+ChatColor.YELLOW+p.getName()+ChatColor.GREEN+" to arena "+ChatColor.YELLOW+args[0]);
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
    public String getPermission()
    {
        return "varenas.addplayer";
    }

    @Override
    public String getDescription()
    {
        return "Adds a player to an arena";
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
