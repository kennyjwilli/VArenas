
package net.vectorgaming.varenas.commands.admin;

import net.vectorgaming.arenakits.KitManager;
import net.vectorgaming.arenakits.framework.Kit;
import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.vcore.framework.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Kenny
 */
public class ArenaAddKitCommand extends SubCommand
{
    public ArenaAddKitCommand()
    {
        super("addkit", ArenaAPI.getPlugin());
    }
    
    @Override
    public void run(CommandSender cs, String[] args)
    {
        Player p = (Player) cs;
        
        if(KitManager.kitExists(args[0].toLowerCase()))
        {
            cs.sendMessage(ChatColor.RED+"Error: Kit "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.RED+" already exists!");
            return;
        }
        
        Kit kit = new Kit(args[0].toLowerCase());
        kit.setInventoryContents(p.getInventory().getContents());
        kit.setArmorContents(p.getInventory().getArmorContents());
        KitManager.addKit(args[0].toLowerCase(), kit);
        
        cs.sendMessage(ChatColor.GREEN+"Successfully added kit "+ChatColor.YELLOW+args[0].toLowerCase()+ChatColor.GREEN+"!");
    }

    @Override
    public String getUsage() {return "/arena addkit <name>";}

    @Override
    public boolean isPlayerOnlyCommand() {return true;}

    @Override
    public String getPermission() {return "varenas.addkit";}

    @Override
    public String getDescription()
    {
        return "Adds a kit to the arenas";
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
