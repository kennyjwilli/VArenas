
package net.vectorgaming.varenas.commands.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.ArenaFramework;
import net.vectorgaming.varenas.framework.enums.ArenaYMLPath;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaSetRegion extends SubCommand
{
    public ArenaSetRegion()
    {
        super("setregion", ArenaAPI.getPlugin());
    }
    
    /*
     * add ability to name trigger box region
     */
    @Override
    public void run(CommandSender cs, String[] args) 
    {
        
        if(!ArenaManager.mapExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Map "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return;
        }
        
        ArenaFramework framework = ArenaManager.getAreanFramework(args[0].toLowerCase());
        
        WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        Selection selection = we.getSelection((Player) cs);
        Location maxY;
        Location minY;
        try
        {
            maxY = selection.getMaximumPoint();
            minY = selection.getMinimumPoint();
        }catch(Exception e)
        {
            cs.sendMessage(ChatColor.RED+"Error: Two points must be selected to set the arena box.");
            return;
        }
        
        try
        {
            framework.setArenaTriggerBox(new PolygonTriggerBox(ArenaManager.getPolygonPoints(minY, maxY), ArenaYMLPath.ARENA_REGION.toString()));
        }catch(Exception e)
        {
            e.printStackTrace();
            cs.sendMessage(ChatColor.RED+"Error: Could not create region");
            return;
        }
        
        cs.sendMessage(ChatColor.GREEN+"Region "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+" for map "+ChatColor.YELLOW+WordUtils.capitalizeFully(args[0])+ChatColor.GREEN+" has been set.");
    }
    
    @Override
    public String getUsage() {return "Usage: /arena setregion <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.setregion";}

    @Override
    public String getDescription()
    {
        return "Sets the region for the map";
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
