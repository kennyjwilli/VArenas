
package net.vectorgaming.varenas.listeners;

import net.vectorgaming.varenas.ArenaAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Kenny
 */
public class PlayerJoinListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event)
    {
        event.getPlayer().teleport(ArenaAPI.getHubWorld().getSpawnLocation());
    }
}
