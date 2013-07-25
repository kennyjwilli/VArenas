
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Kenny
 */
public class PlayerDeathListener implements Listener
{
    public void onDeath(PlayerDeathEvent event)
    {
        Player p = event.getEntity();
        if(!ArenaManager.isPlayerInArena(p)) return;
        
        
    }
}
