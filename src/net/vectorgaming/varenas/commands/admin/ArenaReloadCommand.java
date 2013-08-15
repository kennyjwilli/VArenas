
package net.vectorgaming.varenas.commands.admin;

import java.util.ArrayList;
import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.util.SLAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaReloadCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments)
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        
        ArrayList<String> maps = ArenaManager.getMaps();
        
        for(String a : maps)
        {
            if(!ArenaManager.isMapSavedToConfig(a.toLowerCase()))
                SLAPI.saveArena(a);
        }
        
        ArenaManager.reloadHashMaps();
        
        for(String a : maps)
            SLAPI.loadArena(a);
        
        cs.sendMessage(ChatColor.GREEN+"Successfully reloaded VArenas!");
        return true;
    }

    @Override
    public String getName() {return "arena reload";}

    @Override
    public String getUsage() {return "Usage: /arena reload <type>";}

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
    public String getPermission() {return "varenas.reload";}

}
