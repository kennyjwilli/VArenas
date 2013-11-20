
package net.vectorgaming.varenas.commands.admin;

import java.util.ArrayList;
import net.vectorgaming.arenakits.KitManager;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.util.SLAPI;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Kenny
 */
public class ArenaReloadCommand extends SubCommand
{
    public ArenaReloadCommand()
    {
        super("reload", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {        
        ArrayList<String> maps = ArenaManager.getMaps();
        
        for(String a : maps)
        {
            if(!ArenaManager.isMapSavedToConfig(a.toLowerCase()))
                SLAPI.saveArena(a);
        }
        
        ArenaManager.reloadHashMaps();
        
        for(String a : maps)
            SLAPI.loadArena(a);
        
        for(String s : KitManager.getKitNames())
        {
            if(!KitManager.isSavedToConfig(s))
                KitManager.saveKit(s);
        }
        
        KitManager.loadAllKits();
        
        cs.sendMessage(ChatColor.GREEN+"Successfully reloaded VArenas!");
    }

    @Override
    public String getUsage() {return "/arena reload";}

    @Override
    public boolean isPlayerOnlyCommand() {return false;}

    @Override
    public String getPermission() {return "varenas.reload";}

    @Override
    public String getDescription()
    {
        return "Reloads the arenas from file";
    }

    @Override
    public Integer getMinArgsLength()
    {
        return 0;
    }

    @Override
    public Integer getMaxArgsLength()
    {
        return 0;
    }

}
