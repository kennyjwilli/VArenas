
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.varenas.commands.VCommand;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaCreateCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] args) 
    {
        
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
