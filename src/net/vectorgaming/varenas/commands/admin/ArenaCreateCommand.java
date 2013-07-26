
package net.vectorgaming.varenas.commands.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.*;
import net.vectorgaming.vevents.event.type.EventType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaCreateCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        if(args.length != 1)
        {
            cs.sendMessage(getUsage());
            return true;
        }
        
        if(args.length == 1)
        {
            try {
                ArenaManager.createArena(args[0], EventType.PVP_ARENA.toString());
                cs.sendMessage(ChatColor.GREEN+"Created PvP Arena "+ChatColor.YELLOW+args[0]+" successfully!");
                ArenaManager.getArena(args[0]).checkArenaSetup((Player) cs);
            } catch (Exception ex) {
                Logger.getLogger(ArenaCreateCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    @Override
    public String getName() {return "arena create";}
    
    @Override
    public String getUsage() {return "Usage: /arena create <name>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands(){}

    @Override
    public String getPermission() {return "varena.create";}

}
