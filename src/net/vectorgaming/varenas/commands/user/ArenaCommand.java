
package net.vectorgaming.varenas.commands.user;

import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.commands.admin.ArenaCreateCommand;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] args) {
        if(args.length == 0)
        {
            cs.sendMessage(getUsage());
            return true;
        }
        this.runSubCommand(cs, args[0], args);
        return true;
    }

    @Override
    public String getName() {return "arena";}

    @Override
    public String getUsage() {return "Type /arena help for a list of commands.";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands() {
        this.addSubCommand("create", ArenaCreateCommand.class);
    }

    @Override
    public String getPermission() {return "varena.help";}

}
