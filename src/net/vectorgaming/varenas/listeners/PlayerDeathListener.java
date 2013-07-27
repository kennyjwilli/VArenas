
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
        Player dead = event.getEntity();
        Player killer = dead.getKiller();
        if(!ArenaManager.isPlayerInArena(dead)) return;
        
        Arena arena = ArenaManager.getArenaFromPlayer(dead);
        arena.getStats().recordKill(killer, dead);
        if(arena.getStats().getTotalKills() == 10)
        {
            Bukkit.broadcastMessage("Match over. Cant tell you who won cuz no method for it yet");
        }
    }
}
