
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Kenny
 */
public class PlayerDeathListener implements Listener
{
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        if(!ArenaPlayerManager.isPlayerInArena(event.getEntity())) {
            return;
        }
        Arena arena = ArenaPlayerManager.getArenaFromPlayer(event.getEntity());
        arena.onDeath(event);
    }
}
