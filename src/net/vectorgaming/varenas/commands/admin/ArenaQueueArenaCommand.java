
package net.vectorgaming.varenas.commands.admin;

import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaQueueArenaCommand extends VCommand
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
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        Arena arena = ArenaManager.createArena(args[0].toLowerCase(), false);
        ArenaManager.queueArena(arena);
        cs.sendMessage(ChatColor.GRAY+"---------------------------------");
        cs.sendMessage(ChatColor.GREEN+"Successfully queued map "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.GREEN+".");
        cs.sendMessage(ChatColor.GRAY+"---------------------------------");
        cs.sendMessage(ChatColor.BLUE+"Arena name: "+ChatColor.GOLD+arena.getName());
        cs.sendMessage(ChatColor.GREEN+"To add players to the arena type "+ChatColor.YELLOW+"/arena addplayer "+arena.getName()+" <player>");
        cs.sendMessage(ChatColor.BLUE+"To start the arena type "+ChatColor.YELLOW+"/arena forcestart "+arena.getName());
        
        return true;
    }

    @Override
    public String getName() {return "arena queuearena";}

    @Override
    public String getUsage() {return "Usage: /arena queuearena <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

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
    public String getPermission() {return "varenas.queuearena";}

}
