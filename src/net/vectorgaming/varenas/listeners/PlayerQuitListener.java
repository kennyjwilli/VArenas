
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Kenny
 */
public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
            
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(p);
        Arena arena = ArenaManager.getArena(arenaName);
        
        ArenaPlayerManager.removePlayerFromArena(arenaName, p);
        
        arena.onQuit(event);
    }
}
