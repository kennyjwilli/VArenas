
package net.vectorgaming.varenas.commands.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.ArrayList;
import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.VArena;
import net.vectorgaming.varenas.framework.VRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class SetArenaBoxCommand extends VCommand
{

    @Override
    public boolean run(CommandSender cs, String[] arguments) 
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        if(args.length != 1)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(!ArenaManager.arenaExists(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
            return true;
        }
        
        VArena arena = ArenaManager.getArena(args[0]);
        
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
            

    //        arena.addPolygonPoint(maxY);
    //        arena.addPolygonPoint(minY);
        try
        {
            TriggerBox box = new PolygonTriggerBox(ArenaManager.getPolygonPoints(minY, maxY), arena.getName(), minY.getBlockY(), maxY.getBlockY()) {

                @Override
                public void entered(Entity entity)
                {
                    //add stuff later
                    return;
                }

                @Override
                public void left(Entity entity)
                {
                    //add stuff later
                    return;
                }
            };
            
            arena.setArenaBox(box);
        }catch(Exception e)
        {
            e.printStackTrace();
            cs.sendMessage(ChatColor.RED+"Error: Could not create region");
            return true;
        }
        
        cs.sendMessage(ChatColor.GREEN+"Region for arena "+arena.getName()+" has been set.");
        arena.checkArenaSetup((Player) cs);
        
        
        
        return true;
    }

    @Override
    public String getName() {return "arena setregion";}

    @Override
    public String getUsage() {return "Usage: /arena setregion <arena>";}

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
