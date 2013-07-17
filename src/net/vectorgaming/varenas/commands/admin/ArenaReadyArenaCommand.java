
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
public class ArenaReadyArenaCommand extends VCommand

{

    @Override
    public boolean run(CommandSender cs, String[] arguments) 
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length == 0)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(!ArenaManager.arenaExists(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        Arena arena = ArenaManager.getArena(args[0]);
        
        arena.readyArena();
        
        cs.sendMessage(ChatColor.GREEN+"Arena "+ChatColor.YELLOW+arena.getName()+ChatColor.GREEN+" is now ready to be used!");
        return true;
    }

    @Override
    public String getName() {return "arena readyarena";}

    @Override
    public String getUsage() {return "Usage: /arena readyarena <name>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands() {}

    @Override
    public String getPermission() {return "varena.readyarena";}
}
