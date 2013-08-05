
package net.vectorgaming.varenas.commands.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import info.jeppes.ZoneCore.TriggerBoxes.PolygonTriggerBox;
import info.jeppes.ZoneCore.TriggerBoxes.TriggerBox;
import java.util.Arrays;
import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.commands.VCommand;
import net.vectorgaming.varenas.framework.Arena;
import net.vectorgaming.varenas.framework.config.ArenaConfig;
import org.apache.commons.lang.WordUtils;
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
public class ArenaAddRegion extends VCommand
{
    /*
     * add ability to name trigger box region
     */
    @Override
    public boolean run(CommandSender cs, String[] arguments) 
    {
        String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
        if(args.length != 2)
        {
            cs.sendMessage(ChatColor.RED+getUsage());
            return true;
        }
        
        if(!ArenaManager.arenaExists(args[0]))
        {
            cs.sendMessage(ChatColor.RED+"Error: Arena "+ChatColor.YELLOW+args[0]+ChatColor.RED+" does not exist.");
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
            framework.addTriggerBox("regions."+args[1].toLowerCase(), new PolygonTriggerBox(ArenaManager.getPolygonPoints(minY, maxY), args[1]));
        }catch(Exception e)
        {
            e.printStackTrace();
            cs.sendMessage(ChatColor.RED+"Error: Could not create region");
            return true;
        }
        
        cs.sendMessage(ChatColor.GREEN+"Region "+ChatColor.YELLOW+args[1]+ChatColor.GREEN+" for arena "+ChatColor.YELLOW+WordUtils.capitalizeFully(args[0])+ChatColor.GREEN+" has been set.");
        //arena.checkArenaSetup((Player) cs);
        
        
        
        return true;
    }

    @Override
    public String getName() {return "arena addregion";}

    @Override
    public String getUsage() {return "Usage: /arena addregion <map> <regionName>";}

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
