
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Kenny
 */
public class PlayerRespawnListener implements Listener
{
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(p);
        
        if(!ArenaManager.isArenaRunning(arenaName))
            return;
        Arena arena = ArenaManager.getArena(arenaName);
        arena.onRespawn(event);
    }

}
