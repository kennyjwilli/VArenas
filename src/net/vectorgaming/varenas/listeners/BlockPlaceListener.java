
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author Kenny
 */
public class BlockPlaceListener implements Listener
{
    @EventHandler
    public void onBlockPlayer(BlockPlaceEvent event)
    {
        Player p = event.getPlayer();
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        Arena arena = ArenaPlayerManager.getArenaFromPlayer(p);
        
        if(!arena.isRunning())
            return;
        
        if(!arena.getSettings().isBlockPlaceAllow())
        {
            event.setCancelled(true);
            return;
        }
        
        arena.onBlockPlace(event);
    }
}
