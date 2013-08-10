
package net.vectorgaming.varenas.commands.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import net.vectorgaming.varenas.framework.enums.ArenaYMLPath;
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
public class ArenaSetRegion extends VCommand
{
    /*
     * add ability to name trigger box region
     */
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
        
        ArenaConfig framework = ArenaManager.getArenaConfig(args[0]);
        
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
            return true;
        }
        
        try
        {
            framework.setArenaBox(new PolygonTriggerBox(ArenaManager.getPolygonPoints(minY, maxY), ArenaYMLPath.ARENA_REGION.toString()));
        }catch(Exception e)
        {
            e.printStackTrace();
            cs.sendMessage(ChatColor.RED+"Error: Could not create region");
            return true;
        }
        
        cs.sendMessage(ChatColor.GREEN+"Region "+ChatColor.YELLOW+args[0]+ChatColor.GREEN+" for map "+ChatColor.YELLOW+WordUtils.capitalizeFully(args[0])+ChatColor.GREEN+" has been set.");
        return true;
    }

    @Override
    public String getName() {return "arena setregion";}

    @Override
    public String getUsage() {return "Usage: /arena setregion <map>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String[] getAliases() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setupSubCommands() {}

    @Override
    public String getPermission() {return "varenas.setregion";}
}
