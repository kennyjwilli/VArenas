
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
public class ArenaAddSpawnPointCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments) 
    {
        Player p = (Player) cs;
        
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        if(args.length != 2)
        {
            cs.sendMessage(getUsage());
            return true;
        }
        
        if(!ArenaManager.arenaExists(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        Arena arena = ArenaManager.getArena(args[0]);
        
        arena.addSpawnPoint(args[1], p.getLocation());
        
        cs.sendMessage(ChatColor.GREEN+"Added spawn point "+ChatColor.YELLOW+args[1]+ChatColor.GREEN+" to arena "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+"!");
        return true;
    }

    @Override
    public String getName() {return "arena addspawn";}

    @Override
    public String getUsage() {return "Usage: /arena addspawn <arena> <spawn>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands() {}

    @Override
    public String getPermission() {return "varena.addspawn";}

}
