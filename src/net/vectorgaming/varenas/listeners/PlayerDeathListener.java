
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
//        if(!ArenaManager.isPlayerInArena(event.getEntity())) 
//            return;
//        Arena arena = ArenaManager.getArenaFromPlayer(event.getEntity());
//        arena.onDeath(event);
    }
}
