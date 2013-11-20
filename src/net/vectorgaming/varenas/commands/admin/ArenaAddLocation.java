
package net.vectorgaming.varenas.commands.admin;

import info.jeppes.ZoneCore.TriggerBoxes.Point3D;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaAddLocation extends SubCommand
{
    public ArenaAddLocation()
    {
        super("addlocation", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {                
        if(args.length != 2)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return;
        }
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[1]+ChatColor.RED+" does not exist.");
            return;
        }
        
        ArenaManager.getAreanFramework(args[0].toLowerCase()).addLocation(args[1].toLowerCase(), new Point3D(((Player) cs).getLocation()));
        cs.sendMessage(ChatColor.GREEN+"Successfully added new location for "+ChatColor.YELLOW+args[1].toLowerCase()+ChatColor.GREEN+" in map "+ChatColor.YELLOW+args[0].toLowerCase());
    }

    @Override
    public String getName() {return "arena addlocation";}

    @Override
    public String getUsage(){return "/arena addlocation <map> <locationName>";}

    @Override
    public boolean isPlayerOnlyCommand(){return true;}

    @Override
    public String getPermission(){return "varenas.addlocation";}

    @Override
    public String getDescription()
    {
        return "Adds a location to an arena";
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
