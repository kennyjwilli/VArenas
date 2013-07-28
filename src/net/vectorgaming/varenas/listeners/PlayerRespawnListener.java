
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.Arena;
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
        if(!ArenaManager.isPlayerInArena(event.getPlayer()))
            return;
        Arena arena = ArenaManager.getArenaFromPlayer(event.getPlayer());
        arena.onRespawn(event);
    }

}
