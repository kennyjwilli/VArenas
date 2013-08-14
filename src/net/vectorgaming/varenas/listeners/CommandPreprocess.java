
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaAPI;
import net.vectorgaming.varenas.ArenaPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 *
 * @author Kenny
 */
public class CommandPreprocess implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event)
    {
        Player p = event.getPlayer();
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        String[] args = event.getMessage().split(" ");
        if(p.hasPermission("arenas.override.commands"))
            return;
        
        for(String s : ArenaAPI.getAllowedCommands())
        {
            if(args[0].equalsIgnoreCase("/"+s) || args[0].equalsIgnoreCase("/arena"))
                return;
        }
        p.sendMessage(ChatColor.RED+"You cannot use this command while in an arena!");
        event.setCancelled(true);
    }
}
