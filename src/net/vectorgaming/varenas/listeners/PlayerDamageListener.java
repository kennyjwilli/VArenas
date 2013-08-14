
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author Kenny
 */
public class PlayerDamageListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent event)
    {
        Player p;
        if(event.getEntity() instanceof Player)
        {
            p = (Player) event.getEntity();
        }else
        {
            return;
        }
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(p);
        
        if(!ArenaManager.isArenaRunning(arenaName))
            return;
        
        Arena arena = ArenaManager.getArena(arenaName);
        
        if(event.getDamage() > p.getHealth())
        {
            ArenaPlayerManager.getArenaFromPlayer(p).onDeath(p, event.getEntity());
            if(ArenaManager.getArenaSettings(arena).isShowRespawnScreen())
                return;
            p.setHealth(20D);
            p.teleport(arena.getSpawnLocation(p));
            event.setCancelled(true);
        }
    }
}
