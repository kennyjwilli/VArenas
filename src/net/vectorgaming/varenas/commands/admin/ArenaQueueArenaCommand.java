
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaQueueArenaCommand extends SubCommand
{
    public ArenaQueueArenaCommand()
    {
        super("queuearena", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        if(args.length != 1)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return;
        }
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return;
        }
        
        Arena arena = ArenaManager.createArena(args[0].toLowerCase(), false);
        ArenaManager.queueArena(arena);
        cs.sendMessage(ChatColor.GRAY+"---------------------------------");
        cs.sendMessage(ChatColor.GREEN+"Successfully queued map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.GREEN+".");
        cs.sendMessage(ChatColor.GRAY+"---------------------------------");
        cs.sendMessage(ChatColor.BLUE+"Arena name: "+ChatColor.GOLD+arena.getName());
        cs.sendMessage(ChatColor.GREEN+"To add players to the arena type "+ChatColor.YELLOW+"/arena addplayer "+arena.getName()+" <player>");
        cs.sendMessage(ChatColor.BLUE+"To start the arena type "+ChatColor.YELLOW+"/arena forcestart "+arena.getName());
    }

    @Override
    public String getUsage() {return "Usage: /arena queuearena <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

    @Override
    public String getPermission() {return "varenas.queuearena";}

    @Override
    public String getDescription()
    {
        return "Queues a map";
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
