
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 *
 * @author Kenny
 */
public class ProjectileLaunchListener implements Listener
{
    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event)
    {
        Projectile proj = event.getEntity();
        if(!(proj.getShooter() instanceof Player))
        {
            return;
        }
        
        Player p = (Player) proj.getShooter();
        
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(p);
        
        if(!ArenaManager.isArenaRunning(arenaName))
            return;
        
        Arena arena = ArenaManager.getArena(arenaName);
        arena.onProjectileLaunch(event);
    }
}
