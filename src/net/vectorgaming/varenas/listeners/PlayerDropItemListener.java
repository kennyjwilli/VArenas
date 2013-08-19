
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 *
 * @author Kenny
 */
public class PlayerDropItemListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDrop(PlayerDropItemEvent event)
    {
        Player p = event.getPlayer();
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        Arena arena = ArenaPlayerManager.getArenaFromPlayer(p);
        
        if(!arena.getWorld().getName().equalsIgnoreCase(p.getWorld().getName()))
            return;
        
        if(!arena.isRunning())
            return;
        
        if(arena.getSettings().getAllowedItemDropTypes().contains("ALL"))
            return;
        if(arena.getSettings().getAllowedItemDropTypes().contains("NONE"))
        {
            event.setCancelled(true);
            p.getInventory().remove(event.getItemDrop().getItemStack());
        }
    }
}
