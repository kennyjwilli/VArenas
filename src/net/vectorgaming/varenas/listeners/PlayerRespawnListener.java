
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaManager;
import net.vectorgaming.varenas.ArenaPlayerManager;
import net.vectorgaming.varenas.framework.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Kenny
 */
public class PlayerRespawnListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
        if(!ArenaPlayerManager.isPlayerInArena(p))
            return;
        
        String arenaName = ArenaPlayerManager.getArenaNameFromPlayer(p);
        
        if(!ArenaManager.isArenaRunning(arenaName))
            return;
        Arena arena = ArenaManager.getArena(arenaName);
        if(arena.getSettings().isRespawnWithKit())
            ArenaPlayerManager.getKitFromPlayer(p).giveKit(p, arena.getSettings().isKitClearInventory());
        event.setRespawnLocation(arena.onRespawn(p));
    }

}
