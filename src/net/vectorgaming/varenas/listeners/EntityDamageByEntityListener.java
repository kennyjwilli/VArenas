
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Kenny
 */
public class EntityDamageByEntityListener implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        if(!(event.getEntity() instanceof Player))
        {
            return;
        }
        
        Player p = (Player) event.getEntity();
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(p);
        
        if(!ArenaManager.isArenaRunning(arenaName))
            return;
        
        Arena arena = ArenaManager.getArena(arenaName);
        
        arena.onEntityDamageByEntity(event);
    }
}
